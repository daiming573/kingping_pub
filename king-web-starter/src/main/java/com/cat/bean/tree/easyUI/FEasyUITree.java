package com.cat.bean.tree.easyUI;

import com.cat.bean.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class FEasyUITree {

    /**
     * 加载树
     *
     * @param nodes 节点集合
     *
     * @return List<Tree>
     */
    public List<Tree> getTree(List<Node> nodes) {
        List<Tree> trees = new ArrayList<Tree>();
        if (null == nodes || nodes.isEmpty()) {
            return null;
        }
        for (Node node : nodes) {
            Tree tree = setTree(node);
            trees.add(tree);
        }
        return trees;
    }

    /**
     * 构造树
     *
     * @param node 节点
     *
     * @return Tree
     */
    public Tree setTree(Node node) {
        Tree tree = new Tree();
        tree.setId(node.getId());
        tree.setText(node.getLabel());
        tree.setCode(node.getCode());
        tree.setIconCls(node.getIconName());
        tree.setUrl(node.getDoInvoke());
        tree.setLinkCatalog(node.getLinkCatalog());
        tree.setTypeName(node.getTypeName());
        tree.setRedirectUrl(node.getDoRedirect());
        tree.setOuterCatalog(node.getOuterCatalog());
        tree.setIntroduceUrl(node.getDoIntroduce());
        if ("Y".equals(node.getHasChild())) {
            tree.setState("closed");
        }
        return tree;
    }

}
