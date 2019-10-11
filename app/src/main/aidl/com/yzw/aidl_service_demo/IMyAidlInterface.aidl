package com.yzw.aidl_service_demo;

import com.yzw.aidl_service_demo.Person;

interface IMyAidlInterface {

        void addPerson(in Person person);

        List<Person> getPersonList();

}
