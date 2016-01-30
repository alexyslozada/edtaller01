package digital.escuela.edtaller.pruebas;

import digital.escuela.edtaller.dao.DAOTareas;
import digital.escuela.edtaller.objetos.Tarea;
import java.util.List;

public class Pruebas {
    
    public static void main(String[] args) {
        /*Tarea tarea = new Tarea();
        tarea.setTarea("Probar la clase crear");
        DAOTareas dao = new DAOTareas();
        int idTarea = dao.crearTarea(tarea);
        System.out.println("La tarea quedó creada con el id: "+ idTarea);*/
        
        DAOTareas dao = new DAOTareas();
        List<Tarea> listado = dao.getLista();
        for(Tarea tarea:listado){
            System.out.println("La tarea es: "+tarea.getTarea());
        }
    }
}
