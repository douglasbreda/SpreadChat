/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import java.nio.charset.StandardCharsets;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import spread.*;

/**
 *
 * @author dougl
 */
public class ListenerMessages implements AdvancedMessageListener {

    private JTextArea txtMessages;
    private JList<String> lstUsers;

    public ListenerMessages(JTextArea txtMessages, JList<String> lstUsers) {
        this.txtMessages = txtMessages;
        this.lstUsers = lstUsers;
    }

    
    
    @Override
    public void regularMessageReceived(SpreadMessage sm) {
        txtMessages.setText(txtMessages.getText() + "\n"
                + sm.getSender() + ": " + new String(sm.getData()));
    }

    @Override
    public void membershipMessageReceived(SpreadMessage sm) {
        txtMessages.setText(txtMessages.getText() + "\n"
                + sm.getSender() + ": " + new String(sm.getData()));

        AddListMembers(sm.getMembershipInfo());
    }

    //Add the users to a list
    private void AddListMembers(MembershipInfo pMembers) {
        DefaultListModel<String> model = new DefaultListModel<>();
        lstUsers.setModel(model);

        for (SpreadGroup oGroup : pMembers.getMembers()) {
            model.addElement(oGroup.toString());
        }

    }

}
