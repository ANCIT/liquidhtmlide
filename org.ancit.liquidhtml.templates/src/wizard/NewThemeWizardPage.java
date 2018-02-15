package wizard;

import org.ancit.liquidhtml.labelprovider.MyLabelProvider;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class NewThemeWizardPage extends WizardPage {
	private Text text_1;
	private Text text_2;
	private IProject[] projects;
	private IProject selectedProject;

	/**
	 * @wbp.parser.constructor
	 */
	public NewThemeWizardPage(String pageName) {
		super(pageName);
		setTitle("New Theme Wizard Page");
		setDescription("Enter the theme details");

	}

	public NewThemeWizardPage(String pageName, String title, ImageDescriptor titleImage) {

		super(pageName, title, titleImage);

	}

	@Override
	public void createControl(Composite parent) {
		setPageComplete(false);
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(3, false));

		Label lblFolder = new Label(composite, SWT.NONE);
		lblFolder.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFolder.setText("Project");

		text_1 = new Text(composite, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnBrowse_1 = new Button(composite, SWT.NONE);
		btnBrowse_1.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				ElementListSelectionDialog dialog = new ElementListSelectionDialog(
						Display.getDefault().getActiveShell(), new MyLabelProvider());
				dialog.setTitle("String Selection");
				dialog.setMessage("");
				getProjects();
				dialog.setElements(new Object[] { projects });
				int open = dialog.open();
				if (open == IDialogConstants.OK_ID) {
					IProject[] firstResult = (IProject[]) dialog.getFirstResult();
					selectedProject = firstResult[0];
					String absolutePath = selectedProject.getName();
					text_1.setText(absolutePath);
				}
			}

		});
		btnBrowse_1.setText("Browse");

		Label lblName_1 = new Label(composite, SWT.NONE);
		lblName_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName_1.setText("Theme Name");

		text_2 = new Text(composite, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);

		setControl(parent);

	}

	public IProject[] getProjects() {
		projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		return projects;
	}

	public IProject getSelectedProject() {
		return selectedProject;
	}

	public String getTemplateFileName() {
		return text_2.getText();
	}
	
	@Override
	public IWizardPage getPreviousPage() {
		return null;
	}
	
	@Override
	public boolean isPageComplete() {
		return true;
	}
}
