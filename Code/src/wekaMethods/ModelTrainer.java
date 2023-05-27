package wekaMethods;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;

public class ModelTrainer {

    public LinearRegression trainLinearRegressionModel(Instances trainData) throws Exception {
        LinearRegression lin = new LinearRegression();
        lin.buildClassifier(trainData);
        return lin;
    }

    public Evaluation evaluateLinearRegressionModel(LinearRegression model, Instances testData) throws Exception {
        Evaluation eval = new Evaluation(testData);
        eval.evaluateModel(model, testData);
        return eval;
    }
}
