import pdb
import random
from math import exp
import helpers as h
import decisionTrees as dt

def shuffle_dataset(x,y):
    instances = []
    for i in range(0,len(x)):
        instances.append([x[i],y[i]])
    random.shuffle(instances)
    new_x = [pair[0] for pair in instances]
    new_y = [pair[1] for pair in instances]
    return new_x, new_y

def dot_dense(w,x):
    sum = 0
    for index in w:
        if index in x:
            sum += w[index] * x[index]
    return sum

def scale_dense(weights, multiplier, addition, features):
    
    for index in features:
        if index in weights:
            weights[index] = weights[index] * multiplier + features[index] * addition
        else:
            if addition != 0:
                weights[index] = features[index] * addition

class SVM:
    def predict(self,x):
        dp = dot_dense(self.w, x)
        if dp > 1:
            return 1
        return -1
    
    def fit(self,x,y):
        for epoch in range(self.epochs):
            x, y = shuffle_dataset(x,y)
            for i in range(len(x)):
                features = x[i]
                label = y[i]
                dp = dot_dense(self.w, features)
                if label * dp <= 1:
                    scale_dense(self.w, 1 - self.rate, float(self.rate * self.tradeoff * label), features) 
                else:
                    scale_dense(self.w, 1 - self.rate, 0, features)
                
    def set_params(self,_rate,_tradeoff,_epochs=10):
        self.rate = _rate
        self.tradeoff = _tradeoff
        self.epochs = _epochs
        self.w = {}

    def __init__(self,_rate=10,_tradeoff=10,_epochs=10):
        self.rate = _rate
        self.tradeoff = _tradeoff
        self.epochs = _epochs
        self.w = {}

class LogisticRegression:

    def predict_sparse(self,x):
        bias = self.coef[0]
        for i in range(len(x) - 1):
            bias += self.coef[i + 1] * x[i]
        return 1.0 / (1.0 + exp(-bias))

    def fit_sparse(self,x,y):
        self.coef = [0.0 for i in range(len(x[0]))]
        for epoch in range(self.epochs):
            sum_error = 0
            x, y = shuffle_dataset(x,y)
            for row in x:
                prediction = self.predict_sparse(row)
                error = row[-1] - prediction
                sum_error += error**2
                self.coef[0] += self.rate * error * prediction * (1.0 - prediction)
                for i in range(len(row) - 1):
                    self.coef[i+1] += self.rate * error * prediction * (1.0 - prediction) * row[i]


    def predict_dense(self,x):
        temp_bias = self.bias
        for index in x:
            if index in self.coef:
                temp_bias += self.coef[index] * x[index]
        return 1.0 / (1.0 + exp(-temp_bias))
        
        
    def fit_dense(self,x,y):
        self.bias = 0.0
        self.coef = {}
        for epoch in range(self.epochs):
            sum_error = 0
            x,y = shuffle_dataset(x,y)
            for features, label in zip(x,y):
                prediction = self.predict_dense(features)
                error = label - prediction
                sum_error += error ** 2
                self.bias += self.rate * error * prediction * (1.0 - prediction)
                for index in features:
                    new_val = self.rate * error * prediction * (1.0 - prediction) * features[index]
                    if index in self.coef:
                        self.coef[index] += new_val
                    elif new_val != 0:
                        self.coef[index] = new_val

    def predict(self,x):
        rounded = round(self.predict_dense(x))
        if rounded == 0:
            return -1
        return 1

    def fit(self,x,y):
        self.fit_dense(x,y)
    

    def set_params(self,_rate,_tradeoff=10):
        self.rate = _rate
        self.tradeoff = _tradeoff

    def __init__(self,_rate=1,_tradeoff=.1,_epochs=10):
        self.rate = _rate
        self.tradeoff = _tradeoff
        self.epochs = _epochs

class NaiveBayes:

    def feature_prob(self,x,y):
        return (self.counts[y][x] if x in self.counts[y] else 0 + self.smoothing) / (self.labels[y] + 2 * self.smoothing)
            
    def predict(self,x):
        
        pos_prob = 0.0
        neg_prob = 0.0
        for feature in x:
            pos = self.feature_prob(feature,1)
            neg = self.feature_prob(feature,-1)
            pos_prob += pos
            neg_prob += neg
        if pos_prob > neg_prob:
            return 1
        return -1
    
    def fit(self,x,y):
        self.counts = {
            1 : {},
            -1: {}
        }
        self.labels = {
            1 : 0,
            -1: 0
        }
        for i in range(len(x)):
            label = y[i]
            features = x[i]
            for feature in features:
                if feature in self.counts[label]:
                    self.counts[label][feature] += 1
                else:
                    self.counts[label][feature] = 1
            self.labels[label] += 1

    def set_params(self,_smoothing):
        self.smoothing = _smoothing

    def __init__(self,_smoothing=2):
        self.smoothing = _smoothing

class BaggedForests:
    def predict(self,x):
        instance = h.dense_to_sparse_row(x)
        pos_count = 0
        neg_count = 0
        for tree in self.trees:
            prediction = tree.predict(instance)
            if prediction > 0:
                pos_count += 1
            else:
                neg_count += 1
        if pos_count > neg_count:
            return 1
        return -1
    
    def fit(self,x,y):
        self.trees = x

    def data_row(self,x):
        instance = h.dense_to_sparse_row(x)
        result = []
        for tree in self.trees:
            prediction = tree.predict(instance)
            result.append(prediction)
        return result
    
    def set_params(self,_depth):
        self.depth = _depth

    def __init__(self,_depth=3,_tree_count=1000):
        self.depth = _depth
        self.tree_count = _tree_count

def generate_tree_dataset(x,y):
    forest = BaggedForests()
    forest.fit(x,y)
    rows = []
    for row in x:
        rows.append(forest.data_row(row))
    return h.sparse_to_dense(rows), y

class SVMTree:
    def predict(self,x):
        return 1
    
    def fit(self,x,y):
        pass

    def set_params(self,_rate,_tradeoff,_depth):
        self.rate = _rate
        self.tradeoff = _tradeoff
        self.depth = _depth

    def __init__(self,_rate=1,_tradeoff=10,_depth=50):
        self.rate = _rate
        self.tradeoff = _tradeoff
        self.depth = _depth

class LogisticTree:
    def predict(self,x):
        return 1
    
    def fit(self,x,y):
        pass

    def set_params(self,_rate,_tradeoff,_depth):
        self.rate = _rate
        self.tradeoff = _tradeoff
        self.depth = _depth

    def __init__(self,_rate=1,_tradeoff=.1,_depth=50):
        self.rate = _rate
        self.tradeoff = _tradeoff
        self.depth = _depth