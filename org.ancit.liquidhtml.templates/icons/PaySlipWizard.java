package org.ancit.liquidhtml.templates;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class PaySlipWizard extends Wizard implements INewWizard {

	public PaySlipWizard() {
		
	}
	@Override
	public void addPages() {
		PaySlipWizardPage paySlipWizardPage=new PaySlipWizardPage();
		super.addPages();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		

	}

	@Override
	public boolean performFinish() {
		
		return false;
	}

}
