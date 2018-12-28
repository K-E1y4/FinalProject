package FinalProject2.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.Employee;
import FinalProject2.model.Task;
import FinalProject2.model.TaskDetail;
import FinalProject2.model.TaskDetailSearch;
import FinalProject2.model.TaskDivision;
import FinalProject2.model.TaskManagement;
import FinalProject2.model.TaskSearch;
import FinalProject2.repository.TaskDetailRepository;
import FinalProject2.repository.TaskManagementRepository;
import FinalProject2.repository.TaskRepository;

@Service
public class TaskDetailService {
	
	//20行ごとにページングするように設定
		private static final int PAGE_SIZE=20;

	@Autowired
	TaskDetailRepository taskDetailRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	TaskManagementRepository taskManagementRepository;
	
	@Autowired
	TaskDivisionService taskDivisionService;

	public List<TaskDetail> findByTaskId(String task_id) {
		
		List<TaskDetail> taskDetailList = taskDetailRepository.findByTaskId(task_id);
		
		return taskDetailList;
	}

	public Page<TaskDetail> findAll(int Page) {
		return taskDetailRepository.findAll(PageRequest.of(Page<=0?0:Page, PAGE_SIZE));
	}
	
	public List<TaskDetail> findAll() {
		return taskDetailRepository.findAll();
	}
	
	//検索条件を元に検索
		public Page<TaskDetail> findBySearch(int page_number, TaskDetailSearch taskDetailSearch) {
			
					String task_id = taskDetailSearch.getTask_id();
					String taskDetail_id = taskDetailSearch.getTask_detail_id();
					String sekininsya = taskDetailSearch.getSekininsya();
					
					String due_date_from_str = taskDetailSearch.getDue_date_from();
					LocalDate due_date_from;
					if(due_date_from_str.equals("")) {
						due_date_from = mostPastDueDate();
					}else {
						due_date_from = LocalDate.parse(due_date_from_str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					}
					String due_date_to_str = taskDetailSearch.getDue_date_to();
					LocalDate due_date_to;
					if(due_date_to_str.equals("")) {
						due_date_to = mostPresentDueDate();
					}else {
						due_date_to =  LocalDate.parse(due_date_to_str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					}
					
					String make_date_from_str = taskDetailSearch.getMake_date_from();
					Timestamp  make_date_from;
					if(make_date_from_str.equals("")) {
						make_date_from = mostPastMakeDate();
					} else {
						make_date_from = stringToTimestamp(make_date_from_str);
					}
					String make_date_to_str = taskDetailSearch.getMake_date_to();
					Timestamp make_date_to;
					if(make_date_to_str.equals("")) {
						make_date_to = mostPresentMakeDate();
					} else {
						make_date_to = stringToTimestamp(make_date_to_str);
					}
					
					String end_flg = taskDetailSearch.getEnd_flg();
					Boolean end_flg_A = false; Boolean end_flg_B = false;
					switch(end_flg) {
					case "全て":
						end_flg_A = false;
						end_flg_B = true;
						break;
					case "未完了":
						end_flg_A = false;
						end_flg_B = false;
						break;
					case "完了":
						end_flg_A = true;
						end_flg_B = true;
						break;
					}
					
					return taskDetailRepository.findBySearch(task_id, taskDetail_id, sekininsya, due_date_from, due_date_to, make_date_from, 
							make_date_to, end_flg_A, end_flg_B, PageRequest.of(page_number<=0?0:page_number, PAGE_SIZE));
				
			}
		
		private LocalDate mostPastDueDate() {
			
			LocalDate mostPresentDueDate = LocalDate.now();
			int compareResult;
			for(TaskDetail taskDetail: findAll()) {
				compareResult = mostPresentDueDate.compareTo(taskDetail.getDue_date());
				//compare 小さいは正（古い）、大きいは負（新しい）
				if(compareResult > 0) {
					mostPresentDueDate = taskDetail.getDue_date();
				}
			}
			return mostPresentDueDate;
		}
		
		private LocalDate mostPresentDueDate() {
			
			LocalDate mostPastDueDate = LocalDate.now();
			int compareResult;
			for(TaskDetail taskDetail: findAll()) {
				compareResult = mostPastDueDate.compareTo(taskDetail.getDue_date());
				//compare 小さいは正（古い）、大きいは負（新しい）
				if(compareResult < 0) {
					mostPastDueDate = taskDetail.getDue_date();
				}
			}
			return mostPastDueDate;
		}
		
		private Timestamp mostPresentMakeDate() {
			
			List<TaskDetail> taskDetailList = taskDetailRepository.findAllOrderByMake_dateAsc();
			Timestamp mostPresentMakeDate = taskDetailList.get(taskDetailList.size() - 1).getMake_date();
			return mostPresentMakeDate;
			
		}

		private Timestamp mostPastMakeDate() {
			
			List<TaskDetail> taskDetailList = taskDetailRepository.findAllOrderByMake_dateAsc();
			Timestamp mostPastMakeDate = taskDetailList.get(0).getMake_date();
			return mostPastMakeDate;
		}
		
		public Timestamp stringToTimestamp(String day) {
			try {
				return new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(day).getTime());
			} catch (ParseException e) {
				// 時間の変換失敗
				e.printStackTrace();
				return null;
			}
		}
		
		public String newTaskDetailId() {
			
			List<TaskDetail> taskDetailList = taskDetailRepository.findAll();
			int maxId = 0;
			for(TaskDetail taskDetail: taskDetailList) {
				if(maxId < Integer.parseInt(taskDetail.getTask_detail_id())) {
					maxId = Integer.parseInt(taskDetail.getTask_detail_id());
				}
			}
			String newTaskDetailId = String.format("%10s", maxId + 1).replace(" ", "0");
			return  newTaskDetailId;
		}

		public void save(TaskDetail taskDetail) {
			
			int task_division_id = taskDetail.getTask_division_id();
			TaskDivision taskDivision = taskDivisionService.findById(task_division_id);
			taskDetail.setTaskDivision(taskDivision);
			
			String task_id = taskDetail.getTask_id();
			Optional<Task> task_check = taskRepository.findById(task_id);
			Task task = null;
			if(task_check.isPresent()) {
				task = task_check.get();
			}
			taskDetail.setTask(task);
			
			String task_detail_id = taskDetail.getTask_detail_id();
			List<TaskManagement> taskManagements = taskManagementRepository.findByTaskId(task_detail_id);
			taskDetail.setTaskManagementList(taskManagements);
			
			taskDetailRepository.save(taskDetail);
		}

		public Optional<TaskDetail> findById(String task_detail_id) {
			return taskDetailRepository.findById(task_detail_id);
		}
			
		@Transactional
		public void update(TaskDetail taskDetail) {
			
			int task_division_id = taskDetail.getTask_division_id();
			TaskDivision taskDivision = taskDivisionService.findById(task_division_id);
			taskDetail.setTaskDivision(taskDivision);
			
			String task_id = taskDetail.getTask_id();
			Optional<Task> task_check = taskRepository.findById(task_id);
			Task task = null;
			if(task_check.isPresent()) {
				task = task_check.get();
			}
			taskDetail.setTask(task);
			
			String task_detail_id = taskDetail.getTask_detail_id();
			List<TaskManagement> taskManagements = taskManagementRepository.findByTaskId(task_detail_id);
			taskDetail.setTaskManagementList(taskManagements);
			
			taskDetailRepository.save(taskDetail);
			
		}
		
		@Transactional
		public void delete(String task_detail_id) {
			
			taskManagementRepository.deleteByTaskDetailId(task_detail_id);
			taskDetailRepository.deleteById(task_detail_id);
			
		}

		public List<TaskDetail> getTaskDetailFinishedList(String task_id) {
			
			return taskDetailRepository.getTaskDetailFinishedList(task_id);
		}

		public List<TaskDetail> getTaskDetailNotFinishedList(String task_id) {
			// TODO 自動生成されたメソッド・スタブ
			return taskDetailRepository.getTaskDetailNotFinishedList(task_id);
		}

		public List<TaskDetail> findTaskManagementAddedl() {
			return taskDetailRepository.findTaskManagementAdded();
		}
		
}
