class Parser(object):
    matrix = []

def makeParser(x,y):
    parser = Parser()
    i = 0
    while(i < x):
        parser.matrix.append([])
        row = parser.matrix[i]
        j = 0
        while(j < y):
            row[j].append(j)
            j = j + 1
        i = i + 1
    return parser

p = makeParser(3,3)
for a in matrix:
    for num in a:
        print(num)


'''
print('Hello world')
fname = 'resources\sentences.txt'
with open(fname) as f:
    content = f.readlines()
for item in content:
    print item
'''
