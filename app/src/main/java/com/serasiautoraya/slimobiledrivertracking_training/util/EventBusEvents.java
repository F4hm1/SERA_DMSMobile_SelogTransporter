package com.serasiautoraya.slimobiledrivertracking_training.util;

/**
 * Created by Fahmi Hakim on 15/04/2018.
 * for SERA
 */


public class EventBusEvents {


    public static class createBus<T>{
        private T data;


        public createBus(T content) {
            this.data = content;
        }

        public T getData() {
            return data;
        }
    }

    public static class createSign<T>{
        private T data;


        public createSign(T content) {
            this.data = content;
        }

        public T getData() {
            return data;
        }
    }

    public static class changeFragment{
        private int data;

        public changeFragment(int content)  {
            this.data = content;
        }
        public int getData() {
            return data;
        }

    }

    public static class killFragment{
        public killFragment()  {
        }

    }



}
