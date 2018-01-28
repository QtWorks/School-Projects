
def all_except(folds,test_fold_index):
    result = []
    for i in range(0,len(folds)):
        if i != test_fold_index:
            result.extend(folds[i])
    return result

def k_fold(data,k):
    avg = len(data) / float(k)
    result = []
    last = 0.0
    while last < len(data):
        result.append(data[int(last):int(last + avg)])
        last += avg
    return result
    
def cross_validate(data,k):
    folds = k_fold(data,k)
    total_score = 0.0
    for i in range(0,len(folds)):
        test_data = folds[i]
        training_data = all_except(folds,i)
        model = train_model(training_data)
        score = evaluate_model(model,test_data)
        total_score += score
    return total_score / float(k)


 model = train_model(training_data)
    score = evaluate_model(model,test_data)
    
    all_data = []
    all_data.extend(training_data)
    all_data.extend(test_data)
    
    cv = cross_validate(test_data,10)

    f = extract_features(training_data)
