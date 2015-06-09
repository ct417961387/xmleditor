package org.my.eclipse.xmleditor.ui.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlLabelProvider extends LabelProvider {
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
				
				String values = map.item(j).getNodeName() + " =\"" + map.item(j).getFirstChild().getNodeValue() + "\"";
				
				str += values + " ";
			}
			return str;
   		}
   			
   		return "";
}
}