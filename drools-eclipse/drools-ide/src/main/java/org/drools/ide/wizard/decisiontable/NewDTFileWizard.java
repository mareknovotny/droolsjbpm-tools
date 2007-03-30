package org.drools.ide.wizard.decisiontable;

import org.drools.ide.DroolsIDEPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * A wizard to create a Decision Table scaffold.
 * 
 * @author Michael Neale
 */
public class NewDTFileWizard extends Wizard implements INewWizard {

    private IWorkbench workbench;
    private IStructuredSelection selection;
    private NewDTFilePage mainPage;
    
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        setWindowTitle("New Decision Table");
    	ImageDescriptor desc = DroolsIDEPlugin.getImageDescriptor("icons/dt-large.gif");
        setDefaultPageImageDescriptor(desc);        
    }
    
    public void addPages() {
        mainPage = new NewDTFilePage(workbench, selection);
        addPage(mainPage);
     }

    public boolean performFinish() {
        return mainPage.finish();
    }
    


}
