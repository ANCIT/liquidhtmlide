package org.ancit.liquidhtml.templates.views;

import org.ancit.liquidhtml.templates.views.SampleView.TreeObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;

public class SampleDragListener implements DragSourceListener {

	private TreeViewer viewer;

	public SampleDragListener(TreeViewer viewer) {
		this.viewer = viewer;
		// TODO Auto-generated constructor stub
	}

	 @Override
	    public void dragFinished(DragSourceEvent event) {
	        System.out.println("Finshed Drag");
	    }

	    @Override
	    public void dragSetData(DragSourceEvent event) {
	        // Here you do the convertion to the type which is expected.
	        IStructuredSelection selection = viewer.getStructuredSelection();
	        TreeObject firstElement = (TreeObject) selection.getFirstElement();

	        if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
	            event.data = "{{"+firstElement.getName()+"}}";
	        }

	    }

	    @Override
	    public void dragStart(DragSourceEvent event) {
	        System.out.println("Start Drag");
	    }

}
