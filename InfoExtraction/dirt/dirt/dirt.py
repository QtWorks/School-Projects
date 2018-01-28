import sys
import numpy as np
import operator

triple_db = []
filtered_triples = []
freq = {}
def getSentences(corpusFile):
    with open(corpusFile) as f:
        lines = f.readlines()
        sentences = []
        currentSentence = []
        for line in lines:
            t = line.strip().split(' : ')
            pType = t[0]
            phrase = t[1]
            currentSentence.append([pType,phrase])
            if phrase == '<EOS':
                sentences.append(currentSentence)
                currentSentence = []
        return sentences


def getHeadNoun(words):
    return words.split()[-1].lower()


def append_words(mid):
    result = ''
    for item in mid:
        result += item[1] + ' '
    return result[:-1].lower()


def get_np_idxs(sentence):
    result = []
    for i in range(0,len(sentence)):
        if sentence[i][0] == 'NP':
            result.append(i)
    return result


def add_triple_to_db(db, left, path, right):
    db.append([getHeadNoun(left[1]),append_words(path),getHeadNoun(right[1])])


def getTriples(triple_db, sentence):
    exclusionList = ['is', 'are', 'be', 'was', 'were', 'said', 'have', 'has', 'had',
                     'and', 'or', '>COMMA', '>SQUOTE', '>RPAREN', '>LPAREN',
                     '>PERIOD', '>MINUS', '>AMPERSAND']
    np_idxs = get_np_idxs(sentence)
    for i in range(len(np_idxs)-1):
        if np_idxs[i + 1] - np_idxs[i] > 1:
            left = sentence[np_idxs[i]]
            right = sentence[np_idxs[i+1]]
            path = sentence[np_idxs[i] + 1: np_idxs[i+1]]
            if len(path) == 1:
                if path[0][1] not in exclusionList and path[0][1].lower() not in exclusionList:
                    add_triple_to_db(triple_db, left, path, right)
            else:
                add_triple_to_db(triple_db, left, path, right)


def getAllTriples(sentences):
    for sentence in sentences:
        getTriples(triple_db, sentence)


def get_test_results(test_lines, filtered_distinct):
    scores = []
    for line in test_lines:
        test_scores = {}
        for path in filtered_distinct:
            test_scores[path] = s(line,path)
        scores.append([line,test_scores])
    return scores


def get_spaces(phrase, num_spaces):
    l = num_spaces - len(phrase)
    spaces = ''
    for i in range(0,l):
        spaces += ' '
    return spaces


def top_score_lines(scores, topK):
    lines = []
    sorted_scores = sorted(scores.items(), key=operator.itemgetter(1))
    i = 1
    final_scores = []
    for item in reversed(sorted_scores):
        if i > topK and item[1] != final_scores[topK-1][1]:
            break
        final_scores.append([i,item[0],item[1]])
        i += 1

    for score in final_scores:
        if score[0] == 1:
            value = 1
        else:
            value = '%.15f' % score[2]
        spaces = get_spaces(score[1],50)
        lines.append('{}. {}{}{}\n'.format(score[0],score[1],spaces,value))
    return lines


def write_output_file(distinct_paths, filtered_distinct, paths, filtered_all,test_results):
    with open('output.txt','w') as f:
        lines = [
            '\nFound {} distinct paths, {} after minfreq filtering\n'.format(len(distinct_paths),len(filtered_distinct)),
            'Found {} path instances, {} after minfreq filtering\n'.format(len(paths),len(filtered_all)),
        ]

        for result in test_results:
            test_line = result[0]
            scores = result[1]
            lines.append('\nMOST SIMILAR RULES FOR: {}\n'.format(result[0]))
            if result[0] not in filtered_distinct:
                lines.append('This phrase is not in the triple database.\n')
            else:
                lines.extend(top_score_lines(scores,5))

        f.writelines(lines)


def get_test_lines(path):
    test_lines = []
    with open(path) as f:
        lines = f.readlines()
        for line in lines:
            test_lines.append(line.strip())
    return test_lines


def write_path_db(triple_db):
    with open('triple_db.txt','w') as f:
        for p in triple_db:
            f.write(str(p) + '\n')


def filter_paths(paths, freq, min_freq):
    filtered = []
    for path in paths:
        if freq[path] >= min_freq:
            filtered.append(path)
    return filtered


def getFreq(paths):
    for item in paths:
        if item in freq:
            freq[item] += 1
        else:
            freq[item] = 1


def get_mi_freq(p, x_or_y, w, triples):
    if p is None and w is None:
        return len(triples)
    elif p is None:
        count = 0
        for triple in triples:
            if triple[x_or_y] == w:
                count += 1
        return count
    elif w is None:
        return freq[p]
    else:
        count = 0
        for triple in triples:
            if triple[x_or_y] == w and triple[1] == p:
                count += 1
        return count


def mi(p, x_or_y, w):
    numerator = (get_mi_freq(p, x_or_y, w, filtered_triples) * get_mi_freq(None, x_or_y, None, filtered_triples))
    denominator = (get_mi_freq(p, x_or_y, None, filtered_triples) * get_mi_freq(None, x_or_y, w, filtered_triples))
    if numerator <= 0 or denominator <= 0:
        return 0
    else:
        l = np.log2(float(numerator) / float(denominator))
        if l < 0:
            return 0
        return l

def sim(slot1, slot2, p1, p2, x_or_y):
    intersection = slot1 & slot2
    numerator = 0.0
    for word in intersection:
        numerator += (mi(p1, x_or_y, word) + mi(p2, x_or_y, word))
    denominator = 0.0
    for word in slot1:
        denominator += mi(p1, x_or_y, word)
    for word in slot2:
        denominator += mi(p2, x_or_y, word)
    if numerator == 0 or denominator == 0:
        return 0
    else:
        return numerator / denominator


def slot(path, x_or_y):
    words = set()
    for triple in triple_db:
        if triple[1] == path:
            word = triple[x_or_y]
            if word not in words:
                words.add(word)
    return words


def s(p1,p2):
    x = 0
    y = 2
    s1 = sim(slot(p1,x), slot(p2,x), p1, p2, x)
    s2 = sim(slot(p1,y), slot(p2,y), p1, p2, y)
    return np.sqrt(s1 * s2)



def filter_triples(freq, minfreq):
    for triple in triple_db:
        if freq[triple[1]] >= minfreq:
            filtered_triples.append(triple)


def dirt(corpus,test_lines,minfreq):
    sentences = getSentences(corpus)
    getAllTriples(sentences)
    distinct_paths = [t[1] for t in triple_db]
    paths = [t[1] for t in triple_db]
    getFreq(paths)
    filter_triples(freq,minfreq)

    distinct_paths = [p for p in freq]
    filtered_distinct = filter_paths(distinct_paths, freq, minfreq)
    filtered_all = filter_paths(paths,freq,minfreq)

    test_results = get_test_results(test_lines,filtered_distinct)
    write_output_file(distinct_paths,filtered_distinct,paths,filtered_all,test_results)



def main():
    corpus = sys.argv[1]
    test_lines = get_test_lines(sys.argv[2])
    minfreq = int(sys.argv[3])
    dirt(corpus,test_lines,minfreq)


if __name__ == '__main__':
    main()