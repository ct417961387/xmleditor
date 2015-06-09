package org.my.eclipse.xmleditor.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.w3c.dom.Node;

public class XmlContentProvider implements ITreeContentProvider{

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		if(parentElement instanceof Node )
		{
			Node nodes = (Node)parentElement;
			
			List datas = new ArrayList();
			
			if (nodes != null) {
				//�ӵ�һ���ֽڵ㿪ʼ���������ӽڵ㣬����ѹ�뼯����
				for (Node n = nodes.getFirstChild(); n != null; n = n
					.getNextSibling()) {
					
					if(n != null && n.getNodeType() == Node.ELEMENT_NODE)
						
						datas.add(n);
					}
				return datas.toArray();
			}
		
			//���Ϊ���� ��ֱ�ӷ��ؼ���
		}else if(parentElement instanceof List) {
			return ((List)parentElement).toArray();
		}
//		
		return new Object[0];
	}


	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		if(element instanceof Node) {
			Node node = (Node)element;
			if(node.hasChildNodes())
				return true;
		}
			
		return false;
	}

}
