package com.taxasuicidetdc.taxasuicidecodigo;

import javafx.scene.control.TextArea;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Random;

public class testeWeka {

    private String caminhoDados;
    public Instances dados;

    public testeWeka(String caminhoDados){
        this.caminhoDados = caminhoDados;
    }

    public void leDados() throws Exception {

        DataSource fonte = new DataSource(caminhoDados);
        dados = fonte.getDataSet();

        if(dados.classIndex() == -1){
            dados.setClassIndex(dados.numAttributes() - 1);
        }
    }

    public void imprimeDados(){

        for (int i = 0; i < dados.numInstances(); i++){
            Instance atual = dados.instance(i);
            System.out.println((i + 1) + ": " + atual + "\n");
        }
    }
    public void arvoreDeDecisaoJ48() throws Exception{
        TextArea imprimir = new TextArea();
        J48 tree = new J48();
        tree.buildClassifier(dados);
        System.out.println(tree);
        imprimir.setText("Avaliacao inicial: \n");
        Evaluation avaliacao;
        avaliacao = new Evaluation(dados);
        avaliacao.evaluateModel(tree, dados);
        imprimir.setText("--> Instacias corretas: " + avaliacao.correct() + "\n");
        imprimir.setText("Avaliacao cruzada: \n");
        Evaluation avalCruzada;
        avalCruzada = new Evaluation(dados);
        avalCruzada.crossValidateModel(tree, dados, 10, new Random(1));
        imprimir.setText("--> Instancias corretas CV: " + avalCruzada.correct() + "\n");
    }
    public void InstanceBased() throws Exception{
        //TextArea imprimir = new TextArea();

        IBk k3 = new IBk(3);
        k3.buildClassifier(dados);
        Instance newInst = new DenseInstance(4);
        newInst.setDataset(dados);
        newInst.setValue(0, 7.2);
        newInst.setValue(1, 3.5);
        newInst.setValue(2, 8.2);
        newInst.setValue(3, 5.2);

        double pred = k3.classifyInstance(newInst);
        System.out.println("Predição: " + pred);

        Attribute a = dados.attribute(9);
        String predClass = a.value((int) pred);
        System.out.println("Predição: " + predClass);

    }
}
