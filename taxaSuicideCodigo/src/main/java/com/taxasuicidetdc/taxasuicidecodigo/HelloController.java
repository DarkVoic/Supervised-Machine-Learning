package com.taxasuicidetdc.taxasuicidecodigo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import java.util.Random;
import javafx.scene.control.TextArea;
import java.io.IOException;

// Iniciando Classe
public class HelloController {

    //Atributos do Scene Builder
    @FXML
    private MenuItem menuItemDataSetIncluir;
    @FXML
    private MenuItem menuItemCarregar;
    @FXML
    private MenuItem menuItemExecutarJ48;
    @FXML
    private MenuItem menuItemExecutarIBk;
    @FXML
    private AnchorPane anchorPaneResultado;
    @FXML
    private AnchorPane anchorPaneEntrada;
    @FXML
    private TextField entradaCaminho;
    @FXML
    private Label labelIdade;
    @FXML
    private Label labelAno;
    @FXML
    private Label labelPais;
    @FXML
    private TextField textFieldIdade;
    @FXML
    private ChoiceBox choiceBoxAno;
    @FXML
    private ChoiceBox choiceBoxPais;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private TextArea textAreaResultadoJ48;
    @FXML
    private TextArea textAreaResultadoIBk;


    //Atributo para os teste Weka
    private String caminhoDados;

    // Construtor para 'testeWeka'
    testeWeka weka;

    @FXML
    public void incluirDataSet() throws Exception {

        //"C:\Users\mathe\Universidade PUC\7 - Período - Materias do 6º\Tec.Para Descob.Do Conhecimento\Trabalho\Trabalho_Pratico\taxaSuicide.arff";
        caminhoDados = entradaCaminho.getText();
        weka = new testeWeka(caminhoDados);
        setChoiceBoxAno();
        setChoiceBoxPais();
        weka.leDados();
    }

    @FXML
    public void carregarDataSet() throws Exception {
        mostrarDados();
    }

    @FXML
    public void resultadoJ48() throws Exception {
            textAreaResultadoJ48.setText("");
            J48 tree = new J48();
            tree.buildClassifier(weka.dados);
            textAreaResultadoJ48.appendText(tree.toString());
            textAreaResultadoJ48.appendText("Avaliacao inicial: \n");
            Evaluation avaliacao;
            avaliacao = new Evaluation(weka.dados);
            avaliacao.evaluateModel(tree, weka.dados);
            textAreaResultadoJ48.appendText("--> Instacias corretas: " + avaliacao.correct() + "\n");
            textAreaResultadoJ48.appendText("Avaliacao cruzada: \n");
            Evaluation avalCruzada;
            avalCruzada = new Evaluation(weka.dados);
            avalCruzada.crossValidateModel(tree, weka.dados, 10, new Random(1));
            textAreaResultadoJ48.appendText("--> Instancias corretas CV: " + avalCruzada.correct() + "\n");
    }
    @FXML
    public void mostrarDados() throws Exception{

        getChoiceAno(choiceBoxAno);
        getChoicePais(choiceBoxPais);

    }

    @FXML
    public void setChoiceBoxAno(){
        choiceBoxAno.getItems().addAll("1985","1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996",
                "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016");
    }
    @FXML
    public String getChoiceAno(ChoiceBox<String> choiceBox){
        String ano = choiceBox.getValue();
        System.out.println(ano);
        return ano;
    }
    @FXML
    public void setChoiceBoxPais(){
        choiceBoxPais.getItems().addAll("Albania","Antigua Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Barbados","Belarus","Belgium","Belize",
                "Bosnia Herzegovina","Brazil","Bulgaria","Cabo Verde","Canada","Chile","Colombia","Costa Rica","Croatia","Cuba","Cyprus","Czech","Denmark","Ecuador","El Salvador","Estonia","Fiji",
                "Finland","France","Georgia","Germany","Greece","Grenada","Guatemala","Guyana","Hungary","Iceland","Ireland","Israel","Italy","Jamaica","Japan","Kazakhstan","Kiribati","Kuwait",
                "Kyrgyzstan","Latvia","Lithuania","Luxembourg","Macau","Maldives","Malta","Mauritius","Mexico","Mongolia","Montenegro","Netherlands","New Zealand","Nicaragua","Norway","Oman","Panama",
                "Paraguay","Philippines","Poland","Portugal","Puerto Rico","Qatar","Republic of Korea","Romania","Russian Federation","Lucia","Vincent Grenadines","San Marino","Serbia","Seychelles",
                "Singapore","Slovakia","Slovenia","South Africa","Spain","Sri Lanka","Suriname","Sweden","Switzerland","Thailand","Trinidad Tobago","Turkey","Turkmenistan","Ukraine","United Arab Emirates",
                "United Kingdom","United States","Uruguay","Uzbekistan");
    }
    @FXML
    public String getChoicePais(ChoiceBox<String> choiceBox){
        String pais = choiceBox.getValue();
        System.out.println(pais);
        return pais;
    }
    public static Integer TryParseInt(String someText) {
        try {
            return Integer.parseInt(someText);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
    @FXML
    public void buttonConfirmarParametros() throws Exception {
        textAreaResultadoIBk.setText("");
        IBk k3 = new IBk(3);
        k3.buildClassifier(weka.dados);
        String idade = textFieldIdade.getText();
        String ano = getChoiceAno(choiceBoxAno);
        String pais = getChoicePais(choiceBoxPais);
        String[] parametro = {null, ano, null, idade, null, null, null, null, null};
        double pred;
        Attribute atribute;
        String predClass;
        Instance newInst = new DenseInstance(weka.dados.numAttributes());
        newInst.setDataset(weka.dados);
        for(int i = 0; i < parametro.length; i++){
            int j = parametro[i] != null ? TryParseInt(parametro[i]) : 0;

            if (j == -1) {
                newInst.setValue(i, parametro[i]);
            }else if(j != 0) {
                newInst.setValue(i, j);
            }
        }
        pred = k3.classifyInstance(newInst);
        textAreaResultadoIBk.appendText("Predição: " + parametro.length + "\n");
        Attribute a = weka.dados.attribute(9);
        predClass = a.value((int) pred);
        textAreaResultadoIBk.appendText("Predição: " + pred + "\n" + predClass);
    }
}