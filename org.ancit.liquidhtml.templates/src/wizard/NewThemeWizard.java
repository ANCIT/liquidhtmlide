package wizard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewThemeWizard extends Wizard implements INewWizard {

	private NewThemeWizardPage paySlipWizardPage;

	public NewThemeWizard() {

	}

	@Override
	public void addPages() {
		paySlipWizardPage = new NewThemeWizardPage("");
		addPage(paySlipWizardPage);

	}

	@Override
	public boolean performFinish() {

		IProject selectedProject = paySlipWizardPage.getSelectedProject();
		IFile file = selectedProject
				.getFile(paySlipWizardPage.getTemplateFileName()+".html");
		try {

			StringBuilder builder = new StringBuilder();
			builder.append(
					"<html><head><body><table style='padding: 30px'> <thead> <tr> <td colspan='99'> <table style='margin-bottom: 20px'><tr> <td style='font-weight: bold; font-size: 32px'>{{ title }}</td> {% if business.logo != null %}<td style='text-align: right'><img src='{{ business.logo }}' style='max-height: 150px; max-width: 300px' /></td>{% endif %} </tr></table> <table style='margin-bottom: 20px'><tr> <td> <div><b>{{ recipient.name }}</b> {{ recipient.code }}</div> <div>{{ recipient.address | newline_to_br }}</div> <div>{{ recipient.identifier }}</div> </td> <td style='border-right-width: 1px; padding-right: 20px; text-align: right'> {% for field in fields %} <div style='font-weight: bold'>{{ field.label }}</div> <div style='margin-bottom: 10px'>{{ field.text }}</div> {% endfor %} </td> <td style='padding-left: 20px; width: 1px; white-space: nowrap'> <div style='font-weight: bold'>{{ business.name }}</div> <div>{{ business.address | newline_to_br }}</div> <div>{{ business.identifier }}</div> </td> </tr></table> <div style='font-size: 14px; font-weight: bold; margin-bottom: 20px'>{{ description }}</div> </td> </tr> <tr> {% for column in table.columns %} <td style='font-weight: bold; padding: 5px 10px; text-align: {{ column.align }}; border-left-width: 1px; border-bottom-width: 1px; border-top-width: 1px{% if forloop.last == true %}; border-right-width: 1px{% endif %}{% if column.nowrap %}; width: 80px{% endif %}'>{{ column.label }}</td> {% endfor %} </tr> </thead> <tbody> {% for row in table.rows %} <tr> {% for cell in row.cells %} <td style='padding: 5px 10px; text-align: {{ table.columns[forloop.index0].align }}; border-left-width: 1px{% if forloop.last == true %}; border-right-width: 1px{% endif %}{% if table.columns[forloop.index0].nowrap %}; white-space: nowrap; width: 80px{% endif %}'>{{ cell.text | newline_to_br }}</td> {% endfor %} </tr> {% endfor %} <tr> {% for column in table.columns %} <td style='border-bottom-width: 1px; border-left-width: 1px{% if forloop.last == true %}; border-right-width: 1px{% endif %}'>&nbsp;</td> {% endfor %} </tr> {% for total in table.totals %} <tr> <td colspan='{{ table.columns | size | minus:1 }}' style='padding: 5px 10px; text-align: right{% if total.emphasis == true %}; font-weight: bold{% endif %}'>{{ total.label }}</td> <td style='border-left-width: 1px; white-space: nowrap; border-right-width: 1px; border-bottom-width: 1px; padding: 5px 10px; text-align: right{% if total.emphasis == true %}; font-weight: bold{% endif %}'>{{ total.text }}</td> </tr> {% endfor %} {% for field in custom_fields %} <tr> <td colspan='99'> <div style='font-weight: bold; padding-top: 20px'>{{ field.label }}</div> <div>{{ field.text | newline_to_br }}</div> </td> </tr> {% endfor %} {% if emphasis.text != null and emphasis.positive %} <tr><td colspan='99'><div style='text-align: center; margin-top: 40px'><span style='color: #006400; border-width: 5px; border-color: #006400; padding: 10px; font-size: 20px'>{{ emphasis.text | upcase }}</span></div></td></tr> {% endif %} {% if emphasis.text != null and emphasis.negative %} <tr><td colspan='99'><div style='text-align: center; margin-top: 40px'><span style='color: #FF0000; border-width: 5px; border-color: #FF0000; padding: 10px; font-size: 20px'>{{ emphasis.text | upcase }}</span></div></td></tr> {% endif %} </tbody> </table></body></head></html>");
			if (!file.exists()) {
				byte[] bytes = builder.toString().getBytes();
				InputStream source = new ByteArrayInputStream(bytes);
				file.create(source, IResource.NONE, null);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}


}
