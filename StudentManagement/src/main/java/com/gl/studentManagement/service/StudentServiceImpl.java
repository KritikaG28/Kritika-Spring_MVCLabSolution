package com.gl.studentManagement.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gl.studentManagement.entity.Student;

@Repository
public class StudentServiceImpl implements StudentService {

	private SessionFactory sessionFactory;

	// create session
	private Session session;

	@Autowired
	StudentServiceImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}


	}

	@Override
	@Transactional
	public List<Student> findAll() {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();

		// find all the records from the database table
		List<Student> students=session.createQuery("from Student").list();

		tx.commit();

		//print StudentList
		print(students);

		return students;
	}

	@Override
	@Transactional
	public Student findById(int studentId) {
		// TODO Auto-generated method stub
		Student student = new Student();
		Transaction tx = session.beginTransaction();

		// find record with Id from the database table
		student = session.get(Student.class, studentId);

		tx.commit();


		return student;
	}

	@Override
	@Transactional
	public void save(Student student) {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();

		// save transaction
		session.saveOrUpdate(student);


		tx.commit();


	}

	@Override
	@Transactional
	public void deleteById(int studentId) {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();

		// delete the record for the provided studentId
		Student student = session.get(Student.class, studentId);

		// delete record
		session.delete(student);

		tx.commit();

	}


	// print the loop
	@Transactional
	public void print(List<Student> student) {

		for(Student s:student) 
		{
			System.out.println(s);
		}
	}

}
