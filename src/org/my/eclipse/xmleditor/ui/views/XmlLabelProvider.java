package org.my.eclipse.xmleditor.ui.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlLabelProvider extends LabelProvider {
	public String getText(Object element) {
		if(element instanceof Node){
			//��ȡ��ǰXML �ڵ��еĲ�������map
   			NamedNodeMap map = ((Node)element).getAttributes();
   			if(map == null) return "" ;
			int length = map.getLength();
			//ȡ�øýڵ������
			String str = ((Node)element).getNodeName() + "  " ;
			//ѭ��ȡ�����еĲ��� �������ƺ�ֵ
			for(int j = 0 ; j < length ; j++) {
				
				String values = map.item(j).getNodeName() + " =\"" + map.item(j).getFirstChild().getNodeValue() + "\"";
				
				str += values + " ";
			}
			return str;
   		}
   			
   		return "";
}
}