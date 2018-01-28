import codecs
import pdb
import math
import operator
import numpy as np


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