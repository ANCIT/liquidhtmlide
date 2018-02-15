package org.ancit.liquidhtml.labelprovider;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.jface.viewers.LabelProvider;

public class MyLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof IProject[]) {
			IProject[] project=(IProject[]) element;
			for (IProject iProject : project) {
			
				return iProject.getName();
			}
			
			
		}
		
		return super.getText(element);
	}

}
