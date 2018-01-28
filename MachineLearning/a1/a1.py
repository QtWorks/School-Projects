import codecs
import pdb
import math
import operator
import numpy as np


mock_dataset = [
    [0,0,0,1,1],
    [0,0,1,1,1],
    [0,0,0,0,0],
    [1,0,1,0,1],
    [1,1,0,0,1],
    [1,1,1,1,1],
    [1,1,1,0,1],
    [0,1,0,0,0],
    [0,1,1,0,0],
    [0,1,1,1,1],
    [1,0,0,0,1],
    [1,0,0,1,1],
    [1,1,0,1,1]
]


class Node:

    def add_child(self,path,child_node):
        self.children[path] = child_node

    def make_leaf(self,_sorted_counts):
        self.is_leaf = True
        self.label = _sorted_counts[0][0]
        self.sorted_counts = _sorted_counts

    def add_subtree_label_counts(self,subtree_label_counts):
        for child in self.children.values():
            if child.is_leaf:
                for count in child.sorted_counts:
                    if count[0] in subtree_label_counts:
                        subtree_label_counts[count[0]] += count[1]
                    else:
                        subtree_label_counts[count[0]] = count[1]
            child.add_subtree_label_counts(subtree_label_counts)

    def predict(self,instance):
        index = self.value
        if index == -1:
            return self.label
        key = instance[index]
        if key in self.children:
            child = self.children[key]
            return child.predict(instance)
        else:
            subtree_label_counts = {}
            self.add_subtree_label_counts(subtree_label_counts)
            sorted_subtree_label_counts = sorted(subtree_label_counts.items(), key=operator.itemgetter(1),reverse=True)
            result = sorted_subtree_label_counts[0][0]
            return result

    def get_error(self):
        subtree_label_counts = {}
        self.add_subtree_label_counts(subtree_label_counts)
        sorted_subtree_label_counts = sorted(subtree_label_counts.items(), key=operator.itemgetter(1),reverse=True)
        return float(sorted_subtree_label_counts[1][1]) / float(sorted_subtree_label_counts[0][1] + sorted_subtree_label_counts[1][1])

    def get_max_depth(self):
        if self.is_leaf:
            return 1
        child_depths = []
        for child in self.children.values():
            child_depths.append(child.get_max_depth())
        return 1 + max(child_depths)

    def __init__(self,_value):
        self.value = _value
        self.children = {}
        self.is_leaf = False




def entropy(data):
    data_count = float(len(data))
    distinct_values = set(data)
    sum = 0
    for val in distinct_values:
        val_count = len([d for d in data if d == val])
        p = float(val_count) / data_count
        sum += (-(p) * math.log(p,2))
    return sum       


def information_gain(data, index):
    #Feature
    pairs = [[d[index],d[-1]] for d in data]

    positive_pairs = [p for p in pairs if p[0] == 1]
    negative_pairs = [p for p in pairs if p[0] == 0]

    pair_count = float(len(pairs))

    positive_portion = float(len(positive_pairs)) / pair_count
    negative_portion = float(len(negative_pairs)) / pair_count

    ep = entropy([p[1] for p in positive_pairs])
    en = entropy([p[1] for p in negative_pairs])

    et = entropy([d[-1] for d in data]) 

    result = et - (positive_portion * ep) + (negative_portion * en)

    if result < 0:
        pdb.set_trace()

    return result

def get_sorted_counts(labels):
    val_count = {}
    for l in labels:
        if l in val_count:
            val_count[l] += 1
        else:
            val_count[l] = 1
    sorted_val_count = sorted(val_count.items(), key=operator.itemgetter(1),reverse=True)
    
    return sorted_val_count

def id3(feature_vector,features,depth_limit):
    if len(features) == 0 or depth_limit == 1:
        n = Node(-1)
        sorted_counts = get_sorted_counts([v[-1] for v in feature_vector])
        n.make_leaf(sorted_counts)
        return n
    best_feature = -1
    best_ig = -1
    for i in features:
        ig = information_gain(feature_vector,i)
        if ig > best_ig:
            best_feature = i
            best_ig = ig
    distinct_values = set([v[best_feature] for v in feature_vector])
    n = Node(best_feature)
    filtered_features = set(features)
    filtered_features.remove(best_feature)
    for val in distinct_values:
        filtered_feature_vector = [v for v in feature_vector if v[best_feature] == val]
        n.add_child(val,id3(filtered_feature_vector,filtered_features,depth_limit-1))
    return n


#FEATURE EXTRACTION

def first_longer_than_last(name):
    s = name.split()
    return len(s[0]) > len(s[-1])

def has_middle_name(name):
    s = name.split()
    return len(s) > 2

def first_and_last_same(name):
    first = name.lower().split()[0]
    return first[0] == first[-1]

def first_before_last(name):
    s = name.lower()
    names = s.split()
    first = names[0]
    last = names[-1]
    sorted(names)
    for name in names:
        if name == first:
            return True
        if name == last:
            return False

vowels = {'a','e','i','o','u'}

def second_letter_vowel(name):
    return name[1] in vowels

def even_length_last_name(name):
    first = name.split()[-1]
    return len(first) % 2 == 0

def first_letter_is(name,letter):
    s = name.lower()
    return int(s[0] == letter)

def first_as_long_as_last(name):
    s = name.lower().split()
    return len(s[0]) == len(s[-1])

def contains(instance,c):
    return c in instance

def has_num_words(instance,n):
    return len(instance.split()) == n

def nth_letter_is(n,name,c):
    return len(name) >= n and name.lower()[n] == c

def num_vowels(name):
    count = 0
    for c in name:
        if c in vowels:
            count += 1
    return count

def extract_features(data):
    feature_vector = []
    for label, instance in data:
        features = [
            int(first_longer_than_last(instance)),
            int(has_middle_name(instance)),
            int(first_and_last_same(instance)),
            int(first_before_last(instance)),
            int(second_letter_vowel(instance)),
            int(even_length_last_name(instance)),
            #my features
            
            int(len(instance) % 3 == 0),
            int(first_as_long_as_last(instance)),
            int(contains(instance,'.')),
            int(contains(instance,'-')),
            int(has_num_words(instance,4)),
            int(has_num_words(instance,1)),
            int(nth_letter_is(3,instance,'e')),
            int(nth_letter_is(0,instance,'s')),
            int(nth_letter_is(-1,instance,'y')),
            len(instance),
            num_vowels(instance),
            len(instance.split()[0]),
            len(instance.split()[-1]),
            int(len(instance) % 2 == 0)
            
            
        ]
        l = 0
        if label == u'+':
            l = 1
        features.append(l)
        feature_vector.append(features)
    return feature_vector

def extract_default_features(data):
    feature_vector = []
    for label, instance in data:
        features = [
            int(first_longer_than_last(instance)),
            int(has_middle_name(instance)),
            int(first_and_last_same(instance)),
            int(first_before_last(instance)),
            int(second_letter_vowel(instance)),
            int(even_length_last_name(instance)),
           
        ]
        l = 0
        if label == u'+':
            l = 1
        features.append(l)
        feature_vector.append(features)
    return feature_vector


def read_data(path):
    with codecs.open(path,'r',encoding='utf-8') as f:
        lines = f.readlines()
        return [[line[0:1],line[2:].strip()] for line in lines]


def run_id3(features_vector,depth_limit):
    t = id3(features_vector,range(0,len(features_vector[0])-1),depth_limit)
    return t

def test_id3(tree,test_set):
    correct = 0.0
    total = 0.0
    for f in test_set:
        p = tree.predict(f)
        if p == f[-1]:
            correct += 1
        total += 1
    return correct / total

def cross_validate(depth_limit,use_default):
    dataset_indices = {0,1,2,3}
    datasets = {}
    for i in dataset_indices:
        path = 'Updated_Dataset/Updated_CVSplits/updated_training0%d.txt' % i
        if use_default:
            dataset_features = extract_default_features(read_data(path))
        else:
            dataset_features = extract_features(read_data(path))
        datasets[i] = dataset_features

    scores = []
    
    for dataset_index in datasets:
        temp_indices = set(dataset_indices)
        temp_indices.remove(dataset_index)
        training_data = []
        for index in temp_indices:
            training_data.extend(datasets[index])
        test_data = datasets[dataset_index]
        
        t = run_id3(training_data,depth_limit)
        result = test_id3(t,test_data)
        scores.append(result)
    return np.mean(scores), np.std(scores)
        
def main():

    training_data = read_data('Updated_Dataset/updated_train.txt')
    test_data = read_data('Updated_Dataset/updated_test.txt')

    print '\nUSING MY ADDITIONAL FEATURES\n'

    training_features = extract_features(training_data)
    test_features = extract_features(test_data)

    t = run_id3(training_features,100)
    print 'Train error:',(1-test_id3(t,training_features)), 'Accuracy:',test_id3(t,training_features)

    t_test = run_id3(test_features,100)
    print 'Test error:',(1-test_id3(t_test,test_features)), 'Accuracy:',test_id3(t_test,test_features)

    depth_limits = {1,2,3,4,5,10,15,20}
    print '\nDepth\tAverageScore\tStdDevScore'
    for d in depth_limits:
        mean,std_dev = cross_validate(d,False)
        print '%d\t%f\t%f' % (d,mean,std_dev)

    t = run_id3(training_features,20)
    print '\nBest depth results, trained on training and tested on test dataset:',test_id3(t,test_features)   

    print '\nUSING ONLY SUGGESTED FEATURES\n'

    training_features = extract_default_features(training_data)
    test_features = extract_default_features(test_data)

    t = run_id3(training_features,100)
    print 'Train error:',(1-test_id3(t,training_features)), 'Accuracy:',test_id3(t,training_features)

    t_test = run_id3(test_features,100)
    print 'Test error:',(1-test_id3(t_test,test_features)), 'Accuracy:',test_id3(t_test,test_features)

    depth_limits = {1,2,3,4,5,10,15,20}
    print '\nDepth\tAverageScore\tStdDevScore'
    for d in depth_limits:
        mean,std_dev = cross_validate(d,True)
        print '%d\t%f\t%f' % (d,mean,std_dev)

    t = run_id3(training_features,7)
    print '\nBest depth results, trained on training and tested on test dataset:',test_id3(t,test_features)   

    print ''

   
if __name__ == '__main__':
    main()