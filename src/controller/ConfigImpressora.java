package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class ConfigImpressora implements Initializable {

//    private ACBrPosPrinter posPrinter;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//    private void loadConfig() throws Exception {
//        posPrinter.configLer();
//
//        int modelo = Integer.parseInt(posPrinter.configLerValor(ACBrSessao.PosPrinter, "Modelo"));
//        cmbModelo.setSelectedItem(ACBrPosPrinterModelo.valueOf(modelo));
//        cmbPorta.setSelectedItem(posPrinter.configLerValor(ACBrSessao.PosPrinter, "Porta"));
//        spnColunas.setValue(Integer.parseInt(posPrinter.configLerValor(ACBrSessao.PosPrinter, "ColunasFonteNormal")));
//        spnEspacos.setValue(Integer.parseInt(posPrinter.configLerValor(ACBrSessao.PosPrinter, "EspacoEntreLinhas")));
//        spnBuffer.setValue(Integer.parseInt(posPrinter.configLerValor(ACBrSessao.PosPrinter, "LinhasBuffer")));
//        spnLinhasPular.setValue(Integer.parseInt(posPrinter.configLerValor(ACBrSessao.PosPrinter, "LinhasEntreCupons")));
//
//        cbxControlePorta.setSelected("1".equals(posPrinter.configLerValor(ACBrSessao.PosPrinter, "ControlePorta")));
//        cbxCortarPapel.setSelected("1".equals(posPrinter.configLerValor(ACBrSessao.PosPrinter, "CortaPapel")));
//        cbxTraduzirTags.setSelected("1".equals(posPrinter.configLerValor(ACBrSessao.PosPrinter, "TraduzirTags")));
//        cbxIgnorarTags.setSelected("1".equals(posPrinter.configLerValor(ACBrSessao.PosPrinter, "IgnorarTags")));
//
//        txtArqLog.setText(posPrinter.configLerValor(ACBrSessao.PosPrinter, "ArqLog"));
//
//        int codePage = Integer.parseInt(posPrinter.configLerValor(ACBrSessao.PosPrinter, "PaginaDeCodigo"));
//        cmbCodePage.setSelectedItem(PosPaginaCodigo.valueOf(codePage));
//    }
//    
//    private void saveConfig() throws Exception {
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "Modelo", ((ACBrPosPrinterModelo) cmbModelo.getSelectedItem()).asInt());
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "Porta", cmbPorta.getSelectedItem());
//
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "ColunasFonteNormal", spnColunas.getValue());
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "EspacoEntreLinhas", spnEspacos.getValue());
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "LinhasBuffer", spnBuffer.getValue());
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "LinhasEntreCupons", spnLinhasPular.getValue());
//
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "ControlePorta", cbxControlePorta.isSelected() ? "1" : "0");
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "CortaPapel", cbxCortarPapel.isSelected() ? "1" : "0");
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "TraduzirTags", cbxTraduzirTags.isSelected() ? "1" : "0");
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "IgnorarTags", cbxIgnorarTags.isSelected() ? "1" : "0");
//
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "ArqLog", txtArqLog.getText());
//        posPrinter.configGravarValor(ACBrSessao.PosPrinter, "PaginaDeCodigo", ((PosPaginaCodigo) cmbCodePage.getSelectedItem()).asInt());
//
//        posPrinter.configGravar();
//    }
}
