import numpy as np
import decisionTrees as dt
import pdb
import helpers as h
from threading import Thread
import threading



def get_data_sparse(path):
    feature_count = 70000
    with open(path,'r') as f:
        lines = [line.strip().split() for line in f.readlines()]
        features = []
        labels = []
        i = 0
        while i < len(lines):
            instance = [0 for c in range(0,feature_count)]
            line = lines[i]
            if line[0].strip() == '1':
                labels.append(1)
            else:
                labels.append(-1)
            for f in line[1:]:
                s = f.split(':')
                instance[int(s[0])] = float(s[1])
            features.append(instance)
            i += 1
        return features, labels

def get_data_dense(path):
    with open(path,'r') as f:
        lines = [line.strip().split() for line in f.readlines()]
        features = []
        labels = []
        i = 0
        while i < len(lines):
            instance = {}
            line = lines[i]
            if line[0].strip() == '1':
                labels.append(1)
            else:
                labels.append(-1)
            for f in line[1:]:
                s = f.split(':')
                instance[int(s[0])] = float(s[1])
            features.append(instance)
            i += 1
        return features, labels

get_data = get_data_dense

def get_folds(dir):
    folds = []
    for i in range(0,5):
        x,y = get_data(dir + 'training0{}.data'.format(i))
        folds.append([x,y])
    return folds

lock1 = threading.Lock()
lock2 = threading.Lock()

def get_tree(x,trees,i):
    lock1.acquire()
    sample = h.random_subset(x)
    lock1.release()
    print('Tree: {} started'.format(i+1))
    tree = dt.id3(sample,range(0,len(sample[0])-1),3)
    print('Tree: {} finished'.format(i+1))
    lock2.acquire()
    trees.append(tree)
    lock2.release()

def get_trees(x,y,tree_count=1000,depth=3):
    h.zip_features_labels(x,y)
    trees = []
    threads = []
    for i in range(tree_count):
        t = Thread(target=get_tree,args=(x,trees,i))
        threads.append(t)
        t.start()
    for t in threads:
        t.join()
    return trees

def get_tree_folds(dir):
    tree_folds = []
    transformed_folds = []
    dense_folds = []
    for i in range(0,5):
        x,y = get_data_sparse(dir + 'training0{}.data'.format(i))
        dense_x, dense_y = get_data_dense(dir + 'training0{}.data'.format(i))
        dense_folds.append([dense_x,dense_y])
        trees = get_trees(x,y)
        tree_folds.append([trees,y])
        new_features = []
        for row in x:
            new_feature_row = []
            for tree in trees:
                new_feature_row.append(tree.predict(row))
            new_features.append(h.sparse_to_dense_row(new_feature_row))
        transformed_folds.append([new_features,y])
    return transformed_folds,tree_folds,dense_folds



def evaluate_model(model,_x,_y):
    correct = 0.0
    for i in range(0,len(_x)):
        prediction = model.predict(_x[i])
        #print('Actual {} Predicted {}'.format(_y[i],prediction))
        if prediction == _y[i]:
            correct += 1
    score = correct / float(len(_x))
    return score

def split_data(folds,index):
    training_x = []
    training_y = []
    test_x = []
    test_y = []
    test = []
    for i in range(0,len(folds)):
        if i != index:
            training_x.extend(folds[i][0])
            training_y.extend(folds[i][1])
        else:
            test_x.extend(folds[i][0])
            test_y.extend(folds[i][1])
    return training_x, training_y, test_x, test_y

def cross_validate(model,folds):
    scores = []
    for i in range(0,5):
        training_x, training_y, test_x, test_y = split_data(folds,i)
        model.fit(training_x,training_y)
        scores.append(evaluate_model(model,test_x,test_y))
    print(scores)
    return np.mean(scores)

def cross_validate_trees(model,tree_folds,folds):
    scores = []
    for i in range(0,5):
        training_trees, training_y, test_trees, test_y = split_data(tree_folds,i)
        training_x, training_y, test_x, test_y = split_data(folds,i)
        model.fit(training_trees,training_y)
        scores.append(evaluate_model(model,test_x,test_y))
    print(scores)
    return np.mean(scores)

def evaluate_hypers(model,folds,hypers):
    best_params = []
    best_score = 0
    for param1 in hypers[0]:
        if len(hypers) > 1:
            for param2 in hypers[1]:
                if len(hypers) > 2:
                    for param3 in hypers[2]:
                        model.set_params(param1,param2,param3)
                        score = cross_validate(model,folds)
                        if score > best_score:
                            best_score = score
                            best_params = [param1,param2,param3]
                else:
                    model.set_params(param1,param2)
                    score = cross_validate(model,folds)
                    if score > best_score:
                        best_score = score
                        best_params = [param1,param2]
        else:
            model.set_params(param1)
            score = cross_validate(model,folds)
            if score > best_score:
                best_score = score
                best_params = [param1]
    print(best_score,best_params)
    return best_params  

def evaluate_test_set(model):
    training_x, training_y = get_data('data/speeches.train.liblinear')
    test_x, test_y = get_data('data/speeches.test.liblinear')
    model.fit(training_x,training_y)
    score = evaluate_model(model,test_x,test_y)
    print('On test set: {}'.format(score))
    return score
