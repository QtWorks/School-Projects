import pdb
import numpy as np
import random
from random import shuffle

feature_count = 69

class simple_perceptron():

    def predict(self,features):
        result = self.bias
        for w,f in zip(self.weights,features):
            result += w * f
        return 1.0 if result >= 0 else -1.0

    def update(self,features,label):
        self.update_count += 1
        for i in range(0,len(features)):
            self.weights[i] += (features[i] * label * self.rate)
        self.bias = self.bias + (self.rate * label)

    def fit(self,x,y):
        self.weights = [random.uniform(-0.1,0.1) for i in range(0,len(x[0]))]
        for i in range(0,self.epochs):
            pairs = zip(x,y)
            shuffle(pairs)
            for features, label in pairs:
                prediction = self.predict(features)
                if prediction != label:
                    self.update(features,label)
            if self.test_set is not None:
                correct = 0.0
                total = 0.0
                x, y = self.test_set
                for features, label in zip(x,y):
                    if self.predict(features) == label:
                        correct += 1
                    total += 1
                score = correct / total
                print 'Simple\t%d\t%f' % (i+1,score)
                if score > self.best_epoch_score:
                    self.best_epoch = i + 1
                    self.best_epoch_score = score

    
    def __init__(self,rate=1.0,epochs=10,test_set=None):
        self.rate = rate
        self.bias = random.uniform(-0.1,0.1)
        self.epochs = epochs
        self.test_set = test_set
        self.best_epoch = epochs
        self.best_epoch_score = 0.0
        self.update_count = 0

class dynamic_learning(): 
  
    def predict(self,features):
        result = self.bias
        for w,f in zip(self.weights,features):
            result += w * f
        return 1.0 if result >= 0 else -1.0

    def update(self,features,label):
        self.update_count += 1
        for i in range(0,len(features)):
            self.weights[i] += (features[i] * label * self.rate)
        self.bias = self.bias + (self.rate * label)

    def fit(self,x,y):
        self.weights = [random.uniform(-0.1,0.1) for i in range(0,len(x[0]))]
        for i in range(0,self.epochs):
            pairs = zip(x,y)
            shuffle(pairs)
            for features, label in pairs:
                prediction = self.predict(features)
                if prediction != label:
                    self.update(features,label)
            self.t += 1.0
            self.rate = self.original_rate / (1 + self.t)
            if self.test_set is not None:
                correct = 0.0
                total = 0.0
                x, y = self.test_set
                for features, label in zip(x,y):
                    if self.predict(features) == label:
                        correct += 1
                    total += 1
                score = correct / total
                print 'Dynamic\t%d\t%f' % (i+1,score)
                if score > self.best_epoch_score:
                    self.best_epoch = i + 1
                    self.best_epoch_score = score
    
    def __init__(self,rate=1.0,epochs=10,test_set=None):
        self.rate = rate
        self.original_rate = rate
        self.bias = random.uniform(-0.1,0.1)
        self.epochs = epochs
        self.t = 0.0
        self.test_set = test_set
        self.best_epoch = epochs
        self.best_epoch_score = 0.0
        self.update_count = 0

class margin_perceptron():
    
    def predict(self,features):
        result = self.bias
        for w,f in zip(self.weights,features):
            result += w * f
        return 1.0 if result >= self.margin else -1.0

    def update(self,features,label):
        self.update_count += 1
        for i in range(0,len(features)):
            self.weights[i] += (features[i] * label * self.rate)
        self.bias = self.bias + (self.rate * label)

    def fit(self,x,y):
        self.weights = [random.uniform(-0.1,0.1) for i in range(0,len(x[0]))]
        for i in range(0,self.epochs):
            pairs = zip(x,y)
            shuffle(pairs)
            for features, label in pairs:
                prediction = self.predict(features)
                if prediction != label:
                    self.update(features,label)
            self.t += 1.0
            self.rate = self.original_rate / (1 + self.t)
            if self.test_set is not None:
                correct = 0.0
                total = 0.0
                x, y = self.test_set
                for features, label in zip(x,y):
                    if self.predict(features) == label:
                        correct += 1
                    total += 1
                score = correct / total
                print 'Margin\t%d\t%f' % (i+1,score)
                if score > self.best_epoch_score:
                    self.best_epoch = i + 1
                    self.best_epoch_score = score
    
    def __init__(self,rate=1.0,epochs=10,margin=1.0,test_set=None):
        self.rate = rate
        self.original_rate = rate
        self.bias = random.uniform(-0.1,0.1)
        self.epochs = epochs
        self.margin = margin
        self.t = 0.0
        self.test_set = test_set
        self.best_epoch = epochs
        self.best_epoch_score = 0.0
        self.update_count = 0

class average_perceptron():
    
    def predict(self,features):
        result = self.average_bias
        for w,f in zip(self.average_weights,features):
            result += w * f
        return 1.0 if result >= 0 else -1.0

    def update(self,features,label):
        self.update_count += 1
        for i in range(0,len(features)):
            self.weights[i] += (features[i] * label * self.rate)
        self.bias = self.bias + (self.rate * label)

    def fit(self,x,y):
        self.weights = [random.uniform(-0.1,0.1) for i in range(0,len(x[0]))]
        self.average_weights = [random.uniform(-0.1,0.1) for i in range(0,len(x[0]))]
        for i in range(0,self.epochs):
            pairs = zip(x,y)
            shuffle(pairs)
            for features, label in pairs:
                prediction = self.predict(features)
                if prediction != label:
                    self.update(features,label)
                for j in range(0,len(self.weights)):
                    self.average_weights[j] += self.weights[j]
                self.average_bias += self.bias
            if self.test_set is not None:
                correct = 0.0
                total = 0.0
                x, y = self.test_set
                for features, label in zip(x,y):
                    if self.predict(features) == label:
                        correct += 1
                    total += 1
                score = correct / total
                print 'Average\t%d\t%f' % (i+1,score)
                if score > self.best_epoch_score:
                    self.best_epoch = i + 1
                    self.best_epoch_score = score
    
    def __init__(self,rate=1.0,epochs=10,test_set=None):
        self.rate = rate
        self.bias = random.uniform(-0.1,0.1)
        self.average_bias = random.uniform(-0.1,0.1)
        self.epochs = epochs
        self.test_set = test_set
        self.best_epoch = epochs
        self.best_epoch_score = 0.0
        self.update_count = 0

def get_data(path):
    with open(path,'r') as f:
        lines = [line.strip().split() for line in f.readlines()]
        features = []
        labels = []
        i = 0
        while i < len(lines):
            instance = [0 for c in range(0,feature_count)]
            line = lines[i]
            if line[0] == '+1':
                labels.append(1)
            else:
                labels.append(-1)
            for f in line[1:]:
                s = f.split(':')
                instance[int(s[0])] = float(s[1])
            features.append(instance)
            i += 1
        return features, labels

def print_experiment(p):
    print '\n--------EXPERIMENT %d--------\n' % p
        
def cross_validate(folds,rate,variant,margin):
    
    dataset_indices = set(range(0,len(folds)))

    if variant == 0:
        model = simple_perceptron(rate=rate)
    elif variant == 1:
        model = dynamic_learning(rate=rate)
    elif variant == 2:
        model = margin_perceptron(rate=rate,margin=margin)
    elif variant == 3:
        model = average_perceptron()

    scores = []
    for test_index in dataset_indices:
        training_x = []
        training_y = []
        for training_index in dataset_indices:
            if training_index != test_index:
                training_x.extend(folds[training_index][0])
                training_y.extend(folds[training_index][1])
        test_x = folds[test_index][0]
        test_y = folds[test_index][1]

        model.fit(training_x,training_y)

        total = 0.0
        correct = 0.0

        for features, label in zip(test_x,test_y):
            prediction = model.predict(features)
            if prediction == label:
                correct += 1
            total += 1
        scores.append(correct/total)
    return np.mean(scores)
def subexperiment_one(folds,title,learning_rates,mode,margins):
    print '\n%s Perceptron\n' % title
    best_accuracy = 0.0
    best_rate = None
    best_margin = None
    print 'Learning Rate\tMargin\tScore'
    for r in learning_rates:
        if margins is None:
            score = cross_validate(folds,r,mode,None)
            if score > best_accuracy:
                best_accuracy = score
                best_rate = r
            print '%f\tNone\t%f' % (r,score * 100)
        else:
            for m in margins:
                score = cross_validate(folds,r,mode,m)
                if score > best_accuracy:
                    best_accuracy = score
                    best_rate = r
                    best_margin = m
                print '%f\t%f\t%f' % (r,m,score * 100)

    
    return best_accuracy, best_rate, best_margin

def experiment_one(folds):
    
    print_experiment(1)

    learning_rates = [1.0,0.1,0.01]
    margins = [1.0,0.1,0.01]

    simple_a, simple_r, simple_m = subexperiment_one(folds,'Simple',learning_rates,0,None)
    dynamic_a, dynamic_r, dynamic_m = subexperiment_one(folds,'Dynamic Learning',learning_rates,1,None)
    margin_a, margin_r, margin_m = subexperiment_one(folds,'Margin',learning_rates,2,margins)
    average_a, average_r, average_m = subexperiment_one(folds,'Average',learning_rates,3,None)

    print '\nModel\tLearning Rate\tMargin\tScore\t'

    print '%s\t%f\tNone\t%f' % ('Simple',simple_r,simple_a)
    print '%s\t%f\tNone\t%f' % ('Dynamic',dynamic_r,dynamic_a)
    print '%s\t%f\t%f\t%f' % ('Margin',margin_r,margin_m,margin_a)
    print '%s\t%f\tNone\t%f' % ('AVerage',average_r,average_a)

    return [
        [simple_a,simple_r],
        [dynamic_a,dynamic_r],
        [margin_a,margin_r,margin_m],
        [average_a,average_r]    
    ]

def subexperiment_two(training,dev,title,mode,learning_rate,m):
    if mode == 0:
        model = simple_perceptron(rate=learning_rate,epochs=20,test_set=dev)
    elif mode == 1:
        model = dynamic_learning(rate=learning_rate,epochs=20,test_set=dev)
    elif mode == 2:
        model = margin_perceptron(rate=learning_rate,epochs=20,margin=m,test_set=dev)
    elif mode == 3:
        model = average_perceptron(rate=learning_rate,epochs=20,test_set=dev)
    x, y = training
    model.fit(x,y)
    return model
def experiment_two(hypers,training,dev,test):
    
    print_experiment(2)

    print 'Model\tEpoch\tScore'

    simple_p = subexperiment_two(training,dev,'Simple',0,hypers[0][1],None)
    dynamic_p = subexperiment_two(training,dev,'Dynamic Learning',1,hypers[1][1],None)
    margin_p = subexperiment_two(training,dev,'Margin',2,hypers[2][1],hypers[2][2])
    average_p = subexperiment_two(training,dev,'Average',3,hypers[3][1],None)

    print '\nModel\tBest Epoch\tUpdates'
    print '\nSimple\t%d\t%d\n' % (simple_p.best_epoch,simple_p.update_count)
    print '\nDynamic\t%d\t%d\n' % (dynamic_p.best_epoch,dynamic_p.update_count)
    print '\nMargin\t%d\t%d\n' % (margin_p.best_epoch,margin_p.update_count)
    print '\nAverage\t%d\t%d\n' % (average_p.best_epoch,average_p.update_count)

    return [simple_p.best_epoch,dynamic_p.best_epoch,margin_p.best_epoch,average_p.best_epoch]
def evaluate(title,model,test):
    x,y = test
    correct = 0.0
    total = 0.0
    for features,label in zip(x,y):
        if model.predict(features) == label:
            correct += 1
        total += 1
    score = correct / total
    print '%s\t%f' % (title,score)

def majority_classify(train,test):
    x,y = train
    xt,yt = test

    positive = len([i for i in y if i > 0])
    negative = len([i for i in y if i < 0])

    positive_t = float(len([i for i in yt if i > 0]))
    negative_t = float(len([i for i in yt if i < 0]))

    if positive > negative:
        return positive_t / (positive_t + negative_t)
    else:
        return negative_t / (positive_t + negative_t)

def main():
    dev = get_data('Dataset/phishing.dev')
    train = get_data('Dataset/phishing.train')
    test = get_data('Dataset/phishing.test')
    
    folds = []
    for i in range(0,5):
        x,y = get_data('Dataset/CVSplits/training0%d.data' % i)
        folds.append([x,y])

    hypers = experiment_one(folds)

    best_epochs = experiment_two(hypers,train,dev,test)

    x_train, y_train = train

    print 'Test Set Results\n'

    print 'Model\tScore'
    simple_p = simple_perceptron(rate=hypers[0][1],epochs=best_epochs[0])
    simple_p.fit(x_train,y_train)
    evaluate('Simple',simple_p,test)

    dynamic_p = dynamic_learning(rate=hypers[1][1],epochs=best_epochs[1])
    dynamic_p.fit(x_train,y_train)
    evaluate('Dynamic',dynamic_p,test)

    margin_p = margin_perceptron(rate=hypers[2][1],epochs=best_epochs[2],margin=hypers[2][2])
    margin_p.fit(x_train,y_train)
    evaluate('Margin',margin_p,test)

    average_p = average_perceptron(rate=hypers[3][1],epochs=best_epochs[3])
    average_p.fit(x_train,y_train)
    evaluate('Average',average_p,test)

    print 'Dev Majority', majority_classify(train,dev)
    print 'Test Majority', majority_classify(train,test)  


if __name__ == '__main__':
    main()