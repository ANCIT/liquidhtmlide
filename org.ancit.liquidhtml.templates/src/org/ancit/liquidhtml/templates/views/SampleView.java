package org.ancit.liquidhtml.templates.views;

import java.util.ArrayList;

import javax.inject.Inject;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.ancit.liquidhtml.templates.views.SampleView";

	
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	 
	class TreeObject implements IAdaptable {
		private String name;
		private TreeParent parent;
		
		public TreeObject(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public void setParent(TreeParent parent) {
			this.parent = parent;
		}
		public TreeParent getParent() {
			return parent;
		}
		@Override
		public String toString() {
			return getName();
		}
		@Override
		public <T> T getAdapter(Class<T> key) {
			return null;
		}
	}
	
	class TreeParent extends TreeObject {
		private ArrayList children;
		public TreeParent(String name) {
			super(name);
			children = new ArrayList();
		}
		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}
		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}
		public TreeObject [] getChildren() {
			return (TreeObject [])children.toArray(new TreeObject[children.size()]);
		}
		public boolean hasChildren() {
			return children.size()>0;
		}
	}

	class ViewContentProvider implements ITreeContentProvider {
		private TreeParent invisibleRoot;

		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot==null) initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}
		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent)parent).hasChildren();
			return false;
		}
/*
 * We will set up a dummy model to initialize tree heararchy.
 * In a real code, you will connect to a real model and
 * expose its hierarchy.
 */
		private void initialize() {
			TreeObject to1 = new TreeObject("title");
			TreeObject to2 = new TreeObject("description");
			TreeParent p1 = new TreeParent("Document Specific");
			p1.addChild(to1);
			p1.addChild(to2);
			
			TreeObject to4 = new TreeObject("field.label");
			TreeObject to5 = new TreeObject("field.label");
			TreeParent p2 = new TreeParent("fields (array of document fields like Invoice date, Invoice number, Order number etc.)");
			p2.addChild(to4);
			p2.addChild(to5);
			
			TreeObject to6 = new TreeObject("recipient.name");
			TreeObject to7 = new TreeObject("recipient.code");
			TreeObject to8 = new TreeObject("recipient.address");
			TreeObject to9 = new TreeObject("recipient.identifier");
			TreeParent p3 = new TreeParent("Recipient Details");
			p3.addChild(to6);
			p3.addChild(to7);
			p3.addChild(to8);
			p3.addChild(to9);
			
			TreeObject to10 = new TreeObject("business.name");
			TreeObject to11 = new TreeObject("business.address");
			TreeObject to12= new TreeObject("business.identifier");
			TreeObject to13 = new TreeObject("business.logo");
			TreeParent p4 = new TreeParent("Business Details");
			p4.addChild(to10);
			p4.addChild(to11);
			p4.addChild(to12);
			p4.addChild(to13);
			
			TreeObject to14 = new TreeObject("table");
			TreeObject to15 = new TreeObject("table.columns");
			TreeObject to16= new TreeObject("column.align");
			TreeObject to17 = new TreeObject("column.label");
			TreeObject to18 = new TreeObject("table.rows");
			TreeObject to19= new TreeObject("row.cells");
			TreeObject to20 = new TreeObject("cell.text");
			TreeParent p5 = new TreeParent("Table Details");
			p5.addChild(to14);
			p5.addChild(to15);
			p5.addChild(to16);
			p5.addChild(to17);
			p5.addChild(to18);
			p5.addChild(to19);
			p5.addChild(to20);
			
			TreeParent p6=new TreeParent("Custom Fields");
			TreeObject to21=new TreeObject("custom_fields");
			TreeObject to22=new TreeObject("field.label");
			TreeObject to23=new TreeObject("field.text");
			p6.addChild(to21);
			p6.addChild(to22);
			p6.addChild(to23);
			
			TreeParent p7=new TreeParent("Emphasis (Used for labels like “PAID IN FULL”)");
			TreeObject to24=new TreeObject("emphasis.positive");
			TreeObject to25=new TreeObject("emphasis.negative");
			TreeObject to26=new TreeObject("emphasis.text");
			p7.addChild(to24);
			p7.addChild(to25);
			p7.addChild(to26);
			
			TreeParent p8=new TreeParent("Sample and Example");
			TreeObject to27=new TreeObject("Document title: {{ title }} ");
			TreeObject to28=new TreeObject("Document date label: {{ fields[0].label }} ");
			TreeObject to29=new TreeObject("Document date: {{ fields[0].text }} ");
			TreeObject to30=new TreeObject("Due date label: {{ fields[1].label }} ");
			TreeObject to31=new TreeObject("Due date: {{ fields[1].text }} ");
			TreeObject to32=new TreeObject("Document reference label: {{ fields[2].label }} ");
			TreeObject to33=new TreeObject("Document reference number: {{ fields[2].text }} ");
			TreeObject to34=new TreeObject("Document description field: {{ description }} ");
			TreeObject to35=new TreeObject("Description: {{ table.columns[0].label }} ");
			TreeObject to36=new TreeObject("Qty: {{ table.columns[1].label }} ");
			TreeObject to37=new TreeObject("Unit price: {{ table.columns[2].label }} ");
			TreeObject to38=new TreeObject("Amount: {{ table.columns[3].label }} ");
			
			
			
			

			
			
			TreeParent root = new TreeParent("Manager Variables");
			root.addChild(p1);
			root.addChild(p2);
			root.addChild(p3);
			root.addChild(p4);
			root.addChild(p5);
			root.addChild(p6);
			root.addChild(p7);
			root.addChild(p8);
			
			invisibleRoot = new TreeParent("");
			invisibleRoot.addChild(root);
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}
		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeParent)
			   imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		
		int operations = DND.DROP_COPY| DND.DROP_MOVE;
        Transfer[] transferTypes = new Transfer[]{TextTransfer.getInstance()};
        viewer.addDragSupport(operations, transferTypes , new SampleDragListener(viewer));
		
	viewer.setContentProvider(new ViewContentProvider());
	viewer.setLabelProvider(new ViewLabelProvider());
	viewer.setInput(getViewSite());
	

		// Create the help context id for the viewer's control
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SampleView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		
		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Sample View",
			message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
