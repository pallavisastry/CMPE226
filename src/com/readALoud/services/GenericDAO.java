package com.readALoud.services;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

public abstract class GenericDAO <T,ID extends Serializable>implements IGenericDAO<T,ID>{

}
