package edu.hsalbsig.swarch.justintime.service;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.modelmapper.ModelMapper;

import edu.hsalbsig.swarch.justintime.dao.EmployeeDao;
import edu.hsalbsig.swarch.justintime.dao.MilestoneDao;
import edu.hsalbsig.swarch.justintime.dao.TimeBookingDao;
import edu.hsalbsig.swarch.justintime.dto.TimeBookingDto;
import edu.hsalbsig.swarch.justintime.dto.TimeBookingInputDto;
import edu.hsalbsig.swarch.justintime.entity.TimeBooking;
import edu.hsalbsig.swarch.justintime.exception.EntityNotFoundException;

@RequestScoped
@Stateful
public class TimeBookingService {
	@EJB
	private TimeBookingDao timeBookingDao;
	@EJB
	private MilestoneDao milestoneDao;
	@EJB
	private EmployeeDao employeeDao;

	@Inject
	private ModelMapper mapper;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TimeBookingDto findById(int id) throws EntityNotFoundException {
		return mapper.map(timeBookingDao.findById(id), TimeBookingDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<TimeBookingDto> getAllBookings(int projectId) {
		return timeBookingDao.getAll(projectId).stream().map(x -> mapper.map(x, TimeBookingDto.class))
				.collect(Collectors.toList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TimeBookingDto create(TimeBookingInputDto timeBookingDto) throws EntityNotFoundException {
		TimeBooking timeBooking = mapper.map(timeBookingDto, TimeBooking.class);
		timeBooking.setMilestone(milestoneDao.findById(timeBookingDto.getMilestone()));
		timeBooking.setEmployee(employeeDao.findById(timeBookingDto.getEmployee()));
		
		timeBookingDao.create(timeBooking);
		return mapper.map(timeBooking, TimeBookingDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TimeBookingDto update(TimeBookingInputDto timeBookingDto) throws EntityNotFoundException {
		TimeBooking timeBooking = mapper.map(timeBookingDto, TimeBooking.class);
		timeBooking.setMilestone(milestoneDao.findById(timeBookingDto.getMilestone()));
        timeBooking.setEmployee(employeeDao.findById(timeBookingDto.getEmployee()));
        
		timeBookingDao.update(timeBooking);
		return mapper.map(timeBooking, TimeBookingDto.class);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Integer id) throws EntityNotFoundException {
		timeBookingDao.delete(id);
	}
}
