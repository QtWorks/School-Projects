import pdb
import models
import evaluation as e

folds = e.get_folds('data/CVSplits/')

'''

print('\n\n\n##SVM##\n\n\n')
bp1 = e.evaluate_hypers(models.SVM(),folds,[
    [10**1,10**0,10**-1,10**-2,10**-3,10**-4],
    [10**1,10**0,10**-1,10**-2,10**-3,10**-4]
])
e.evaluate_test_set(models.SVM(bp1[0],bp1[1]))

print('\n\n\n##Logistic Regression##\n\n\n')
bp2 = e.evaluate_hypers(models.LogisticRegression(),folds,[
    [10**0,10**-1,10**-2,10**-3,10**-4,10**-5],
    [10**-1,10**0,10**1,10**2,10**3,10**4]
])
e.evaluate_test_set(models.LogisticRegression(bp2[0],bp2[1]))

print('\n\n\n##Naive Bayes##\n\n\n')
bp3 = e.evaluate_hypers(models.NaiveBayes(),folds,[
    [2,1.5,1,.5]
])
e.evaluate_test_set(models.NaiveBayes(bp3[0]))
'''

transformed_folds, tree_folds, dense_folds = e.get_tree_folds('data/CVSplits/')
print('\n\n\n##Bagged Forests##\n\n\n')
bf_score = e.cross_validate_trees(models.BaggedForests(),tree_folds,dense_folds)
print(bf_score)

print('\n\n\n##SVM over Trees##\n\n\n')
bp4 = e.evaluate_hypers(models.SVM(),transformed_folds,[
    [10**0,10**-1,10**-2,10**-3,10**-4,10**-5],
    [10**1,10**0,10**-1,10**-2,10**-3,10**-4,10**-5]
])
print('Best hypers: {}'.format(bp4))
e.evaluate_test_set(models.SVM(bp4[0],bp4[1]))

print('\n\n\n##Logistic Regression over Trees##\n\n\n')
bp5 = e.evaluate_hypers(models.LogisticRegression(),transformed_folds,[
    [10**0,10**-1,10**-2,10**-3,10**-4,10**-5],
    [10**-1,10**0,10**1,10**2,10**3,10**4]
])
print('Best hypers: {}'.format(bp5))

e.evaluate_test_set(models.LogisticRegression(bp5[0],bp5[1]))