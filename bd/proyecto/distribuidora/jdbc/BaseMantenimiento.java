//Soure packages/bd.proyecto.distribuidora.jdbc/BaseMantenimiento.java
package bd.proyecto.distribuidora.jdbc;

import java.sql.Connection;
import java.util.List;

public abstract class BaseMantenimiento<T> {
    
    protected Connection conn;

    public BaseMantenimiento(Connection conn) {
        this.conn = conn;
    }
    public abstract boolean adicionar(T obj);
    public abstract boolean modificar(T obj);
    public abstract boolean eliminar(int id);
    public abstract List<T> listar();
    public abstract boolean inactivar(int id);
    public abstract boolean reactivar(int id);

}
