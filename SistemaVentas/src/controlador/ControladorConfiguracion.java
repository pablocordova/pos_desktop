
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Usuarios;
import modelo.UsuariosDAO;
import vista.JFConfiguracion;
import vista.JFConfiguracionUsuariosCrear;

public class ControladorConfiguracion {
    JFConfiguracion vistaConfiguracion;
    UsuariosDAO modeloUsuarios;
    public ControladorConfiguracion(JFConfiguracion vistaConfiguracion, UsuariosDAO modeloUsuarios) {
        this.vistaConfiguracion = vistaConfiguracion;
        this.modeloUsuarios = modeloUsuarios;
        
        vistaConfiguracion.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        this.vistaConfiguracion.btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        this.vistaConfiguracion.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
    }
    
    public void InicializarConfiguracion() {
        ArrayList<Usuarios> arrayUsuarios = new ArrayList<Usuarios>();
        arrayUsuarios= modeloUsuarios.getUsuarios();
        DefaultTableModel model = (DefaultTableModel) vistaConfiguracion.tbUsuarios.getModel();
        for (int i = 0; i < arrayUsuarios.size(); i++) {
            model.addRow(new Object[]{arrayUsuarios.get(i).id, arrayUsuarios.get(i).dni, arrayUsuarios.get(i).password, arrayUsuarios.get(i).apellidos, arrayUsuarios.get(i).nombres, arrayUsuarios.get(i).privilegios});
        }
    }
    
    public void btnCrearActionPerformed(ActionEvent evt) {
        JFConfiguracionUsuariosCrear vistaConfiguracionUsuariosCrear = new JFConfiguracionUsuariosCrear();
        ControladorConfiguracionUsuariosCrear configuracionUsuariosCrear = new ControladorConfiguracionUsuariosCrear(modeloUsuarios, vistaConfiguracionUsuariosCrear, this);
        configuracionUsuariosCrear.InicializarConfiguracionUsuariosCrear();
        vistaConfiguracionUsuariosCrear.setVisible(true);
        vistaConfiguracionUsuariosCrear.setLocationRelativeTo(null);
    }
    
    public void btnEliminarActionPerformed(ActionEvent evt) {
        // Eliminar el usuario que es seleccionado
        DefaultTableModel model = (DefaultTableModel) vistaConfiguracion.tbUsuarios.getModel();
        int selectedRowIndex = vistaConfiguracion.tbUsuarios.getSelectedRow();
        // Caso no se ha seleccionado alguna fila de la tabla
        if(selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Usuario para eliminar");
            return;
        }
        // Remover de la DB
        modeloUsuarios.eliminarUsuario((int) model.getValueAt(selectedRowIndex, 0));
        // Remover de la vista
        model.removeRow(selectedRowIndex);
    }
    
    void limpiaTabla(){
        try{
            DefaultTableModel temp = (DefaultTableModel) vistaConfiguracion.tbUsuarios.getModel();
            int a =temp.getRowCount();
            for(int i=0; i<a; i++)
                temp.removeRow(0);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
