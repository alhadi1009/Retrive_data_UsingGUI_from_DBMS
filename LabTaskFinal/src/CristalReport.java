
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

 import org.apache.pdfbox.pdmodel.PDDocument;
 import org.apache.pdfbox.pdmodel.PDPage;
 import org.apache.pdfbox.pdmodel.PDPageContentStream;
 import org.apache.pdfbox.pdmodel.font.PDType1Font;



/**
 *
 * @author Al Hadi
 */
public class CristalReport extends javax.swing.JFrame {

    private MidClass M;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CristalReport.class.getName());

    /**
     * Creates new form CristalReport
     */
    public CristalReport(MidClass mid) {
        initComponents();
        this.M = mid;
    }

    public void generatePDF() {

        try {
            PDDocument document = new PDDocument();

            DefaultTableModel model
                    = (DefaultTableModel) Table2.getModel();

            for (int i = 0; i < model.getRowCount(); i++) {

                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream content
                        = new PDPageContentStream(document, page);

                content.setFont(PDType1Font.HELVETICA, 12);
                content.beginText();
                content.setLeading(20f);
                content.newLineAtOffset(50, 700);

                content.showText("Student ID : "
                        + model.getValueAt(i, 0));
                content.newLine();

                content.showText("Name : "
                        + model.getValueAt(i, 1));
                content.newLine();

                content.showText("Department : "
                        + model.getValueAt(i, 2));
                content.newLine();

                content.showText("District : "
                        + model.getValueAt(i, 3));
                content.newLine();

                content.showText("Account Number : "
                        + model.getValueAt(i, 4));
                content.newLine();

                content.showText("Balance : "
                        + model.getValueAt(i, 5));
                content.newLine();

                content.endText();
                content.close();
            }

document.save("D:\\ALL Downloads//MyReport.pdf");
            document.close();

            JOptionPane.showMessageDialog(this,
                    "PDF Generated Successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "PDF Error : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Table2 = new javax.swing.JTable();
        btnSubmit2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Dept", "District", "AccountNumber", "Balance"
            }
        ));
        jScrollPane1.setViewportView(Table2);

        btnSubmit2.setText("Preview");
        btnSubmit2.addActionListener(this::btnSubmit2ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(237, 237, 237)
                .addComponent(btnSubmit2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btnSubmit2)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmit2ActionPerformed
                  generatePDF();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSubmit2ActionPerformed

    public void tableContent2() {
        if (M.getFrom() == null || M.getFrom().isEmpty() || M.getTo() == null || M.getTo().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Warning: Two IDs are required!");
            return;
        }

        try {
            //  JDBC connection
            final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
            final String DB_USER = "system";
            final String DB_PASS = "MyNewPass123";

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String checkQuery = "SELECT COUNT(*) FROM StudentEducationalStatus WHERE StudentID IN (?, ?)";
            PreparedStatement ps = con.prepareStatement(checkQuery);
            ps.setString(1, M.getFrom());
            ps.setString(2, M.getTo());
            ResultSet rsCheck = ps.executeQuery();
            rsCheck.next();
            int count = rsCheck.getInt(1);

            if (count < 2) {
                JOptionPane.showMessageDialog(this, "Warning: One or both IDs do not exist");
                return;
            }
            JOptionPane.showMessageDialog(this, "Connected to database successfully");
            CallableStatement cs = con.prepareCall("{ call FindStudentFullInformationSheet(?,?,?) }");

            cs.setString(1, M.getFrom());
            cs.setString(2, M.getTo());
            cs.registerOutParameter(3, -10);

            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(3);
            DefaultTableModel model = (DefaultTableModel) Table2.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("StudentID"),
                    rs.getString("StudentName"),
                    rs.getString("StudentDepartment"),
                    rs.getString("District"),
                    rs.getString("AccountNumber"),
                    rs.getString("AccountBalance")
                });

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table2;
    private javax.swing.JButton btnSubmit2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
