package exemplo.listacontato.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Contato implements Parcelable {

    private String nome = "";
    private String endereco = "";
    private String telefone = "";
    private String referencia = "";
    private String celular = "";
    private String foto = "";
    private String email = "";

    public Contato() {
    }

    public Contato(Parcel in) {
        String [] data = new String[7];
        in.readStringArray(data);
        setNome(data[0]);
        setEndereco(data[1]);
        setTelefone(data[2]);
        setReferencia(data[3]);
        setCelular(data[4]);
        setFoto(data[5]);
        setEmail(data[6]);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                getNome(), getEndereco(), getTelefone(),
                getReferencia(), getCelular(),getFoto(),
                getEmail()
        });
    }

    public static final Parcelable.Creator<Contato> CREATOR = new Parcelable.Creator<Contato>(){

        @Override
        public Contato createFromParcel(Parcel source) {
            return new Contato(source);
        }

        @Override
        public Contato[] newArray(int size) {
            return new Contato[size];
        }
    };
}
