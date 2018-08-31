package lifecycle.icesi.edu.co.listas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

//RecyclerView

public class Adaptador extends BaseAdapter {

    ArrayList<Estudiante> estudiantes;
    Activity activity;

    public Adaptador(Activity activity){
        this.activity = activity;
        estudiantes = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return estudiantes.size();
    }

    @Override
    public Object getItem(int i) {
        return estudiantes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View renglon = inflater.inflate(R.layout.renglon, null, false);
        TextView item_nombre = renglon.findViewById(R.id.item_nombre);
        TextView item_telefono = renglon.findViewById(R.id.item_telefono);

        item_nombre.setText(estudiantes.get(position).getNombre());
        item_telefono.setText(estudiantes.get(position).getTelefono());

        return renglon;
    }

    public void agregarEstudiante(Estudiante e){
        estudiantes.add(e);
        notifyDataSetChanged();
    }



    public String getJson(){
        Gson gson= new Gson();
        String json=gson.toJson(estudiantes);
        return json;
    }

    public void clear() {

        estudiantes.clear();
        notifyDataSetChanged();
    }
}
