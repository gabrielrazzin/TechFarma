package br.com.techfarma.model.cadastro;

import java.sql.Date;

public class ClienteM {
    private int idFilial, idConvenio, idVendedor;
    private String CPF, nome, identidade, CNPJ, insEst, email, telefone, celular, CEP, rua, numero, bairro, compl, UF, codUF, municipio, codMun, sexo, status, cartaoFid;
    private Date dataNasc, dataCad;
    private double limCred;

    public ClienteM(int idFilial, String CPF, String nome, String identidade, String CNPJ, String insEst, Date dataNasc, String telefone, String celular, String email, String CEP, String rua, String numero, String bairro, String compl, String UF, 
            String codUF, String municipio, String codMun, String sexo, String status, int idVendedor, int idConvenio, double limCred, String cartaoFid, Date dataCad){
        
        this.idFilial = idFilial;
        this.CPF = CPF;
        this.nome = nome;
        this.identidade = identidade;
        this.CNPJ = CNPJ;
        this.insEst = insEst;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.CEP = CEP;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.compl = compl;
        this.UF = UF;
        this.codUF = codUF;
        this.municipio = municipio;
        this.codMun = codMun;
        this.sexo = sexo;
        this.status = status;
        this.idVendedor = idVendedor;
        this.idConvenio = idConvenio;
        this.limCred = limCred;
        this.cartaoFid = cartaoFid;
        this.dataCad = dataCad;
    }
    
    public ClienteM(int idFilial, String CPF, String nome, String CNPJ, String insEst, Date dataNasc, String telefone, String celular, String email, String CEP, String rua, String numero, String bairro, String compl, String UF, String codUF, String municipio, 
            String codMun, String sexo, String status, int idConvenio, double limCred, String cartaoFid, Date dataCad){
        this.idFilial = idFilial;
        this.CPF = CPF;
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.insEst = insEst;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.CEP = CEP;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.compl = compl;
        this.UF = UF;
        this.codUF = codUF;
        this.municipio = municipio;
        this.codMun = codMun;
        this.sexo = sexo;
        this.status = status;
        this.idConvenio = idConvenio;
        this.limCred = limCred;
        this.cartaoFid = cartaoFid;
        this.dataCad = dataCad;
    }

    @Override
    public String toString() {
        return idFilial + "\n" + CPF + "\n" + nome + "\n" + identidade + "\n" + CNPJ + "\n" + insEst + "\n" + dataNasc + "\n" + telefone + "\n" + celular + "\n" + email + "\n" + CEP + "\n" + rua + "\n" + numero + "\n" + bairro + "\n" + compl + "\n" + 
                UF + "\n" + codUF + "\n" + municipio + "\n" + codMun + "\n" + sexo + "\n" + status + "\n" + idVendedor + "\n" + idConvenio + "\n" + limCred + "\n" + cartaoFid + "\n" + dataCad;
    }

    public int getIdFilial() {
        return idFilial;
    }

    public int getIdConvenio() {
        return idConvenio;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public String getCPF() {
        return CPF;
    }

    public String getNome() {
        return nome;
    }

    public String getIdentidade() {
        return identidade;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public String getInsEst() {
        return insEst;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCelular() {
        return celular;
    }

    public String getCEP() {
        return CEP;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCompl() {
        return compl;
    }

    public String getUF() {
        return UF;
    }

    public String getCodUF() {
        return codUF;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getCodMun() {
        return codMun;
    }

    public String getSexo() {
        return sexo;
    }

    public String getStatus() {
        return status;
    }

    public String getCartaoFid() {
        return cartaoFid;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public Date getDataCad() {
        return dataCad;
    }

    public double getLimCred() {
        return limCred;
    }
}
