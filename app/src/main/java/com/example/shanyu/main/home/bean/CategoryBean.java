package com.example.shanyu.main.home.bean;

import java.io.Serializable;
import java.util.List;

public class CategoryBean implements Serializable {

    /**
     * id : 1
     * name : 史学
     * parent_id : 0
     * list : [{"id":14,"name":"英语单词","parent_id":13},{"id":15,"name":"数奥","parent_id":13}]
     */

    private int id;
    private String name;
    private int parent_id;
    private List<ListBean> list;

    public static class ListBean implements Serializable {
        /**
         * id : 14
         * name : 英语单词
         * parent_id : 13
         */

        private int id;
        private String name;
        private int parent_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }
}
