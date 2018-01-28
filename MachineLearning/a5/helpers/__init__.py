import pdb
import random

def sparse_to_dense(sparse):
    rows = []
    for row in sparse:
        new_row = {}
        for i in range(len(row)):
            item = row[i]
            if item != 0:
                new_row[i] = item
        rows.append(new_row)
    return rows

def sparse_to_dense_row(sparse_row):
    new_row = {}
    for i in range(len(sparse_row)):
        item = sparse_row[i]
        if item != 0:
            new_row[i] = item
    return new_row

def get_dimensionality(dense):
    max_dim = 0
    for row in dense:
        for index in row:
            if index > max_dim:
                max_dim = index
    return max_dim

def dense_to_sparse(dense):
    rows = []
    for row in dense:
        rows.append(dense_to_sparse_row(row))
    return rows

def dense_to_sparse_row(dense_row):
    dim = 70000 #get_dimensionality(dense)
    new_row = [0.0 for i in range(dim)]
    for index in dense_row:
        new_row[index] = dense_row[index]
    return new_row


def zip_features_labels(x,y):
    for i in range(len(x)):
        x[i].append(y[i])

def random_subset( iterator, K=100 ):
    result = []
    N = 0

    for item in iterator:
        N += 1
        if len( result ) < K:
            result.append( item )
        else:
            s = int(random.random() * N)
            if s < K:
                result[ s ] = item

    return result

    
