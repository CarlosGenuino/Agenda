package exemplo.listacontato.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import exemplo.listacontato.R;
import exemplo.listacontato.adapter.ContatoAdapter;
import exemplo.listacontato.adapter.ReclycleOnItemClickListener;
import exemplo.listacontato.dao.ContatoDao;
import exemplo.listacontato.model.Contato;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_NEW = 1;
    private final int REQUEST_EDIT = 2;

    private ContatoDao dao;
    private RecyclerView recyclerContato;
    private ContatoAdapter adapter;
    private List<Contato> contatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContatoEditActivity.class);
                intent.putExtra(getString(R.string.contato), new Contato());
                startActivityForResult(intent, REQUEST_NEW);
            }
        });
        dao = new ContatoDao(this);
        contatos = dao.getListarContato(ContatoDao.ASC);
        recyclerContato = findViewById(R.id.recycle_contatos);
        recyclerContato.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerContato.setLayoutManager(llm);
        adapter = new ContatoAdapter(contatos);
        recyclerContato.setAdapter(adapter);
        recyclerContato.addOnItemTouchListener(new ReclycleOnItemClickListener(this,
                new ReclycleOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        abrirOpcoes(contatos.get(position));
                    }
                }));
    }

    private void abrirOpcoes(final Contato contato) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(contato.getNome());
        builder.setItems(new CharSequence[]{"Ligar", "Editar", "Excluir"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+ contato.getTelefone()));
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(MainActivity.this, ContatoEditActivity.class);
                                intent1.putExtra(getString(R.string.contato), new Contato());
                                startActivityForResult(intent1, REQUEST_EDIT);
                                break;
                            case 2:
                                //Excluir
                                contatos.remove(contato);
                                dao.deleteContato(contato);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_NEW && resultCode == RESULT_OK){
            //criar contato
            Contato contato = data.getParcelableExtra("contato");
            dao.insertContato(contato);
            contatos = dao.getListarContato(ContatoDao.ASC);
            adapter = new ContatoAdapter(contatos);
            recyclerContato.setAdapter(adapter);
        } else if(requestCode == REQUEST_EDIT && resultCode == RESULT_OK){
            //editar contato
            Contato contato = data.getParcelableExtra("contato");
            dao.alterContato(contato);
            contatos = dao.getListarContato(ContatoDao.ASC);
            adapter = new ContatoAdapter(contatos);
            recyclerContato.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
