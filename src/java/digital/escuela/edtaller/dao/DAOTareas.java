package digital.escuela.edtaller.dao;

import digital.escuela.edtaller.objetos.Tarea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DAOTareas {
    private Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet resultados;
    
    public int crearTarea(Tarea tarea){
        int respuesta = 0;
        try{
            String consulta = "INSERT INTO tareas (tarea) VALUES (?) RETURNING id";
            getConexion();
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, tarea.getTarea());
            resultados = sentencia.executeQuery();
            if(resultados.next()){
                respuesta = resultados.getInt(1);
            }
        } catch (SQLException ex){
            System.out.println("Hubo un error: "+ex.getMessage());
        } finally {
            closeConexion();
        }
        return respuesta;
    }
    
    public List<Tarea> getLista(){
        List<Tarea> listado = new ArrayList<>();
        try{
            String consulta = "SELECT id, tarea, realizada FROM tareas";
            getConexion();
            sentencia = conexion.prepareStatement(consulta);
            resultados = sentencia.executeQuery();
            while(resultados.next()){
                Tarea tarea = new Tarea();
                tarea.setId(resultados.getInt(1));
                tarea.setTarea(resultados.getString(2));
                tarea.setRealizada(resultados.getBoolean(3));
                listado.add(tarea);
            }
        } catch (SQLException ex){
            System.out.println("Hubo un error: "+ex.getMessage());
        } finally {
            closeConexion();
        }
        
        return listado;
    }
    
    public boolean realizaTarea(Tarea tarea){
        boolean actualizada = false;
        try{
            String consulta = "UPDATE tareas set realizada = true where id = ?";
            getConexion();
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, tarea.getId());
            int realizado = sentencia.executeUpdate();
            if(realizado == 1){
                actualizada = true;
            }
        } catch (SQLException ex){
            System.out.println("Hubo un error: "+ex.getMessage());
        } finally {
            closeConexion();
        }
        return actualizada;
    }
            
    private void getConexion(){
        try{
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://127.0.0.1/edtaller";
            Properties propiedades = new Properties();
            propiedades.setProperty("user", "edtaller");
            propiedades.setProperty("password", "edtaller");
            conexion = DriverManager.getConnection(url, propiedades);
        } catch (ClassNotFoundException | SQLException ex){
            System.out.println("Hubo un error: "+ex.getMessage());
        }
    }
    
    private void closeConexion(){
        try{
            if(resultados != null){
                resultados.close();
            }
            if(sentencia != null){
                sentencia.close();
            }
            if(conexion != null){
                conexion.close();
            }
        } catch (SQLException ex){}
    }
}
