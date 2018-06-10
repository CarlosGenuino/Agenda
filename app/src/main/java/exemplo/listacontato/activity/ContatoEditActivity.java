package exemplo.listacontato.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import exemplo.listacontato.R;
import exemplo.listacontato.model.Contato;

public class ContatoEditActivity extends AppCompatActivity {

    private ImageButton foto;
    private TextView nome;
    private TextView endereco;
    private TextView telefone;
    private TextView celular;
    private TextView email;
    private TextView referencia;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato_edit);
        contato = getIntent().getParcelableExtra(getString(R.string.contato));
        foto = findViewById(R.id.fotoContato);
        nome = findViewById(R.id.nomeContato);
        endereco = findViewById(R.id.enderecoContato);
        telefone = findViewById(R.id.telefoneContato);
        celular = findViewById(R.id.celularContato);
        email = findViewById(R.id.emailContato);
        referencia = findViewById(R.id.referenciaContato);
        Button buttonSalvar = findViewById(R.id.buttonSalvarContato);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contato.setNome(nome.getText().toString());
                contato.setEndereco(endereco.getText().toString());
                contato.setTelefone(telefone.getText().toString());
                contato.setCelular(celular.getText().toString());
                contato.setEmail(email.getText().toString());
                contato.setReferencia(referencia.getText().toString());
                if(contato.getNome() == null || contato.getNome().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.validacao_nome, Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(getString(R.string.contato), contato);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaImagem();
            }
        });

        nome.setText(contato.getNome());
        endereco.setText(contato.getEndereco());
        telefone.setText(contato.getTelefone());
        celular.setText(contato.getCelular());
        email.setText(contato.getEmail());
        referencia.setText(contato.getReferencia());
    }

    private void alertaImagem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alertImagem);
        builder.setPositiveButton(R.string.camera, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tirarFoto();
            }
        });

        builder.setNegativeButton(R.string.galeria, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carregarGaleria();
            }
        });
        builder.create().show();
    }

    private void tirarFoto(){

    }

    private void carregarGaleria(){

    }
}
