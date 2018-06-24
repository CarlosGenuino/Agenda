package exemplo.listacontato.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import exemplo.listacontato.model.Contato;

public class ContatoDao extends SQLiteOpenHelper{

    private static final int VERSAODB= 1;
    private static final String TABELA = "CONTATO";
    private static final String DATABASE = "AGENDACONTATOS";

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    public ContatoDao(Context context) {
        super(context, DATABASE, null, VERSAODB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABELA
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nome TEXT NOT NULL,"
                + " email TEXT,"
                + " referencia TEXT,"
                + " foto TEXT,"
                + " celular TEXT,"
                + " endereco TEXT,"
                +" telefone TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Contato> getListarContato(String ordem){
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("select * from "+ TABELA + " order by nome " + ordem + ";", null);
        while (cursor.moveToNext()){
            Contato contato = new Contato();
            contato.setId(cursor.getLong(cursor.getColumnIndex("id")));

            contato.setId(cursor.getLong(cursor.getColumnIndex("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contato.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            contato.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            contato.setCelular(cursor.getString(cursor.getColumnIndex("celular")));
            contato.setReferencia(cursor.getString(cursor.getColumnIndex("referencia")));
            contato.setFoto(cursor.getString(cursor.getColumnIndex("foto")));
            contatos.add(contato);
        }
        return contatos;
    }

    public void  insertContato(Contato contato){
        ContentValues contentValues = new ContentValues();

        contentValues.put("nome", contato.getNome());
        contentValues.put("endereco", contato.getEndereco());
        contentValues.put("email", contato.getEmail());
        contentValues.put("telefone", contato.getTelefone());
        contentValues.put("celular", contato.getCelular());
        contentValues.put("referencia", contato.getReferencia());
        contentValues.put("foto", contato.getFoto());

        getWritableDatabase().insert(TABELA, null, contentValues);
    }

    public void  alterContato(Contato contato){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", contato.getId());
        contentValues.put("nome", contato.getNome());
        contentValues.put("endereco", contato.getEndereco());
        contentValues.put("email", contato.getEmail());
        contentValues.put("telefone", contato.getTelefone());
        contentValues.put("celular", contato.getCelular());
        contentValues.put("referencia", contato.getReferencia());
        contentValues.put("foto", contato.getFoto());
        String[] idToUpdate = {contato.getId().toString()};

        getWritableDatabase().update(TABELA, contentValues, "id = ?", idToUpdate);
    }

    public void  deleteContato(Contato contato){

        String[] idToDelete = {contato.getId().toString()};

        getWritableDatabase().delete(TABELA, "id = ?", idToDelete);
    }
}
