package org.my.eclipse.xmleditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


/**
 * SWT中显示完整的xml树
 * @author Administrator
 *
 */
public class XmlTree {
    static String label = null;
    static String property = null;
	public XmlTree(Composite parent , String path) {
		TreeViewer viewer = new TreeViewer(parent);
		/**
		 * 标签管理
		 */
		viewer.setLabelProvider(new LabelProvider() {
			
			public String getText(Object element) {
				if(element instanceof Node){
					//获取当前XML 节点中的参数集合map
	       			NamedNodeMap map = ((Node)element).getAttributes();
	       			if(map == null) return "" ;
					int length = map.getLength();
					//取得该节点的名称
					String str = ((Node)element).getNodeName() + "  " ;
					//循环取出所有的参数 包含名称和值
					for(int j = 0 ; j < length ; j++) {						
						//String values = map.item(j).getNodeName() + " =\"" + map.item(j).getFirstChild().getNodeValue() + "\"";
						String values = map.item(j).getNodeName();
						String values1 = map.item(j).getFirstChild().getNodeValue();
						label += values + " ";
						property += values1 + " ";					
					}
					return str;
	       		}
	       			
	       		return "";
			}
			
		});
		
		/**
		 * 内容管理器
		 */
		viewer.setContentProvider(new ITreeContentProvider() {
			
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean hasChildren(Object element) {
				
				if(element instanceof Node) {
					Node node = (Node)element;
					if(node.hasChildNodes())
						return true;
				}
					
				return false;
			}
			
			@Override
			public Object getParent(Object element) {
				// TODO Auto-generated method stub
				return null;
			}
			
			
			@Override
			public Object[] getElements(Object inputElement) {
				//System.out.println("########");
				System.out.println();
				return getChildren(inputElement);
			}
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Object[] getChildren(Object parentElement) {
				//判断父节点是否为 XML中节点
				if(parentElement instanceof Node )
				{
					Node nodes = (Node)parentElement;
					
					List datas = new ArrayList();
					
					if (nodes != null) {
						//从第一个字节点开始遍历所有子节点，并且压入集合中
						for (Node n = nodes.getFirstChild(); n != null; n = n
							.getNextSibling()) {
							
							if(n != null && n.getNodeType() == Node.ELEMENT_NODE)
								
								datas.add(n);
							}
						return datas.toArray();
					}
				
					//如果为集合 则直接返回集合
				}else if(parentElement instanceof List) {
					return ((List)parentElement).toArray();
				}
				return new Object[0];
			}
		});
         
		//设置input
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		FileInputStream input = null;
		try {
			builder = factory.newDocumentBuilder();
			input = new FileInputStream(new File(path));
			Document doc = builder.parse(input); 
			
			//自动打开2级节点
			viewer.setAutoExpandLevel(2);
	        viewer.setInput(doc.getChildNodes());
	        viewer.addDoubleClickListener(new IDoubleClickListener(){
	        	public void doubleClick(DoubleClickEvent event) {
	        		Viewer viewer = event.getViewer();
	        		Shell shell = viewer.getControl().getShell();
	        		MessageDialog.openInformation(shell, "Double click",
	        		"Double click detected");
	        		}
	        });
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.MAX | SWT.CLOSE);
		shell.setLayout(new FillLayout());
		shell.setText("XMLTREE");
		new XmlTree(shell, "student.xml");	
		final Canvas c = new Canvas(shell,SWT.NONE);
		shell.open();
		String[] labels = label.split(" ");
		for(int i = 0;i<labels.length;i++){
			final Text t = new Text(c,SWT.BORDER|SWT.WRAP);
			t.setBounds(25,20+i*30,140,20);
			t.setText(labels[i]);
			//System.out.println(labels[i]);
		}
		String[] property1 = property.split(" ");
		for(int j =0 ;j<property1.length;j++){
			final Text t1 = new Text(c,SWT.BORDER|SWT.WRAP);
			t1.setBounds(180,20+j*30,140,20);
			t1.setText(property1[j]);
			//System.out.println(property1[j]);
		}
		final Button b=new Button(c,SWT.PUSH);
		b.setBounds(450, 400, 40, 20);
		b.setText("save");
		while (!shell.isDisposed())
			if (display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
	
}
