package org.kenny.drillmgt.dao;

import java.util.ArrayList;
import java.util.List;

import org.nutz.castor.Castors;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.MappingField;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpression;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * �������ݿ������
 * 
 * @author Administrator
 * 
 */
@IocBean
public class BasicDao {

	@Inject
	protected Dao dao;

	/**
	 * ����Idɾ������
	 * 
	 * @param <T>
	 * @param id
	 *            �־û�Id
	 * @return true �ɹ�ɾ��һ������,falseɾ��ʧ��
	 */
	public <T> boolean delById(int id, Class<T> c) {
		return dao.delete(c, id) == 1;
	}

	/**
	 * ����ID��ѯһ������
	 * 
	 * @param <T>
	 * @param id
	 *            �־û�Id
	 * @param c
	 *            Ҫ��ѯ�ı�
	 * @return ��ѯ���Ķ���
	 */
	public <T> T find(int id, Class<T> c) {
		return dao.fetch(c, id);
	}

	/**
	 * ��ѯ���ݿ��е�ȫ������
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�ı�
	 * @param orderby
	 *            desc ���������
	 * @return List
	 */
	public <T> List<T> search(Class<T> c, String orderby) {
		return dao.query(c, Cnd.orderBy().desc(orderby), null);

	}

	/**
	 * ����������ѯ���ݿ�����������������
	 * 
	 * @param <T>
	 * @param c
	 * @param condition
	 * @return
	 */
	public <T> List<T> search(Class<T> c, Condition condition) {
		return dao.query(c, condition, null);

	}

	/**
	 * ��ҳ��ѯ������������
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�ı�
	 * @param currentPage
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ÿҳ��ʾ����
	 * @param orderby
	 *            desc���������
	 * @return List
	 */
	public <T> List<T> searchByPage(Class<T> c, int currentPage, int pageSize,
			String orderby) {
		Pager pager = dao.createPager(currentPage, pageSize);

		return dao.query(c, Cnd.orderBy().desc(orderby), pager);
	}

	/**
	 * ��ҳ��������ѯ��������
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�ı�
	 * @param condition
	 *            ��ѯ����,��Cnd�ľ�̬��������
	 * @param currentPage
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ÿҳ��ʾ��������
	 * @return List
	 */
	public <T> List<T> searchByPage(Class<T> c, Condition condition,
			int currentPage, int pageSize) {
		Pager pager = dao.createPager(currentPage, pageSize);

		return dao.query(c, condition, pager);
	}

	/**
	 * �޸�һ������
	 * 
	 * @param <T>
	 * @param t
	 *            �޸����ݿ��е�����
	 * @return true �޸ĳɹ�,false �޸�ʧ��
	 */
	public <T> boolean update(T t) {
		return dao.updateIgnoreNull(t) == 1;
	}

	/**
	 * ���������޸�ָ������
	 * 
	 * @param <T>
	 * @param c
	 *            ���ݿ��
	 * @param chain
	 *            �޸ĵ�����
	 * @param condition
	 *            ѡ������
	 * @return true �ɹ�,falseʧ��
	 */
	public <T> boolean update(Class<T> c, Chain chain, Condition condition) {
		return dao.update(c, chain, condition) > 0;
	}

	/**
	 * ����һ������
	 * 
	 * @param <T>
	 * @param t
	 * @return �������ӵ����ݿ����������
	 */
	public <T> T save(T t) {
		return dao.insert(t);
	}

	public void save(String table, Chain chain) {
		dao.insert(table, chain);
	}

	/**
	 * ��ѯ���ݿ��е���������
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�����ݿ��
	 * @return int
	 */
	public <T> int searchCount(Class<T> c) {
		return dao.count(c);
	}

	/**
	 * ����������ѯ���ݿ��е���������
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�����ݿ��
	 * @param condition
	 *            ����,��Cnd�ľ�̬��������
	 * @return int
	 */
	public <T> int searchCount(Class<T> c, Condition condition) {
		return dao.count(c, condition);
	}

	/**
	 * ��������ҳ��
	 * 
	 * @param count
	 *            ��¼����
	 * @param pageSize
	 *            ÿҳ��ʾ��������
	 * @return int
	 */
	public int maxPageSize(int count, int pageSize) {
		if (pageSize > 0) {
			if ((count % pageSize) != 0) {
				return (count / pageSize) + 1;
			} else {
				return (count / pageSize);
			}
		}
		return 0;
	}

	/**
	 * ���ݶ��id ��ѯ����
	 * 
	 * @param <T>
	 * @param ids
	 *            Ҫ��ѯ��id,�����","�����ţ��ָ�
	 * @param c
	 *            Ҫ��ѯ�ı���Ϣ
	 * @return List
	 */
	public <T> List<T> searchByIds(Class<T> c, String ids, String orderby) {
		Entity<T> entity = dao.getEntity(c);

		String id = entity.getIdField().getColumnName();

		String sql = " " + id + " in (" + ids + ") order by " + orderby
				+ " desc";

		return dao.query(c, Cnd.wrap(sql), null);

	}

	/**
	 * ���ݶ��id ��ѯ����
	 * 
	 * @param <T>
	 * @param ids
	 *            ���ε�id����
	 * @param c
	 *            Ҫ��ѯ�ı���Ϣ
	 * @return List
	 */
	public <T> List<T> searchByIds(Class<T> c, int[] ids, String orderby) {
		Entity<T> entity = dao.getEntity(c);

		String id = entity.getIdField().getColumnName();

		return dao.query(c, Cnd.where(id, "in", ids).desc(orderby), null);

	}

	/**
	 * ���ݶ��idɾ������
	 * 
	 * @param <T>
	 * @param c
	 *            Ҫ�����ı���Ϣ
	 * @param ids
	 *            Ҫɾ����id,�����","�����ţ��ָ�
	 * @return true �ɹ�,false ʧ��
	 */
	public <T> void deleteByIds(Class<T> c, String ids) {
		Entity<T> entity = dao.getEntity(c);

		String table = entity.getTableName();

		String id = entity.getIdField().getColumnName();

		Sql sql = Sqls.create("delete from " + table + " where " + id + " in("
				+ ids + ")");

		dao.execute(sql);
	}

	/**
	 * ������������һ������
	 * 
	 * @param <T>
	 * @param condition
	 *            ��ѯ������Cnd����
	 * @return T
	 */
	public <T> T findByCondition(Class<T> c, Condition condition) {
		return dao.fetch(c, condition);
	}

	/**
	 * ģ����ҳ��ѯ����
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�����ݿ��
	 * @param value
	 *            ��ѯ���ֶ�
	 * @param orderby
	 *            ��������
	 * @param currentPage
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ҳ���С
	 * @return List
	 */
	public <T> List<T> searchPageByLike(Class<T> c, String value,
			String orderby, int currentPage, int pageSize) {
		Entity<T> entity = dao.getEntity(c);

		List<MappingField> fields = entity.getMappingFields();

		SqlExpressionGroup group = null;

		for (MappingField f : fields) {
			if (!f.isId()) {
				SqlExpression e = Cnd.exp(f.getColumnName(), "LIKE", "%"
						+ value + "%");
				if (group == null) {
					group = Cnd.exps(e);
				} else {
					group.or(e);
				}
			}
		}

		Pager pager = dao.createPager(currentPage, pageSize);

		return dao.query(c, Cnd.where(group).desc(orderby), pager);
	}

	/**
	 * ģ����ҳ��ѯ���ݼ�¼����
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�����ݿ��
	 * @param value
	 *            ��ѯ���ֶ�
	 * @param orderby
	 *            ��������
	 * @param currentPage
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ҳ���С
	 * @return List
	 */
	public <T> int searchPageByLike(Class<T> c, String value) {
		Entity<T> entity = dao.getEntity(c);

		List<MappingField> fields = entity.getMappingFields();

		SqlExpressionGroup group = null;

		for (MappingField f : fields) {
			if (!f.isId()) {
				SqlExpression e = Cnd.exp(f.getColumnName(), "LIKE", "%"
						+ value + "%");
				if (group == null) {
					group = Cnd.exps(e);
				} else {
					group.or(e);
				}
			}
		}

		return dao.count(c, Cnd.where(group));
	}

	/**
	 * ����ָ�����ֶ�ģ����ҳ��ѯ����
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�ı�
	 * @param fieldName
	 *            �ֶ�����
	 * @param value
	 *            ģ������
	 * @param currentPage
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ÿҳ������
	 * @return List
	 */
	public <T> List<T> searchByPageLike(Class<T> c, String fieldName,
			String value, int currentPage, int pageSize) {
		Entity<T> entity = dao.getEntity(c);

		String column = entity.getField(fieldName).getColumnName();

		Pager pager = dao.createPager(currentPage, pageSize);

		return dao
				.query(c, Cnd.where(column, "LIKE", "%" + value + "%"), pager);

	}

	/**
	 * ����ָ�����ֶ�ģ����ҳ��ѯ���� ��¼����
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�ı�
	 * @param fieldName
	 *            �ֶ�����
	 * @param value
	 *            ģ������
	 * @param currentPage
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ÿҳ������
	 * @return List
	 */
	public <T> int searchByPageLike(Class<T> c, String fieldName, String value) {
		Entity<T> entity = dao.getEntity(c);

		String column = entity.getField(fieldName).getColumnName();

		return dao.count(c, Cnd.where(column, "LIKE", "%" + value + "%"));

	}

	/**
	 * ����ĳ��������ҳ��ѯ����
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ�ı�
	 * @param fieldName
	 *            ƥ���ֶ���
	 * @param value
	 *            ƥ���ֵ
	 * @param currentPage
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ÿҳ������
	 * @return List
	 */
	public <T> List<T> searchByPage(Class<T> c, String fieldName, String value,
			int currentPage, int pageSize) {
		Entity<T> entity = dao.getEntity(c);

		String column = entity.getField(fieldName).getColumnName();

		Pager pager = dao.createPager(currentPage, pageSize);

		return dao.query(c, Cnd.where(column, "=", value), pager);
	}

	/**
	 * ����ָ����������һ������
	 * 
	 * @param <T>
	 * @param fileName
	 *            ƥ������
	 * @param value
	 *            ƥ��ֵ
	 * @return T
	 */
	public <T> T findByCondition(Class<T> c, String fileName, String value) {
		return dao.fetch(c, Cnd.where(fileName, "=", value));
	}

	/**
	 * ���һ�����ݵ����ݿ��У� �����ݰ��������Ķ����������
	 * 
	 * @param <T>
	 * @param t
	 *            �������ݿ�Ķ���
	 * @param fieldName
	 *            �������ݵ��ֶ�����һ��ΪList����
	 * @return T
	 */
	public <T> T saveWidth(T t, String fieldName) {
		return dao.insertWith(t, fieldName);

	}

	/**
	 * ��ȡ��������
	 * 
	 * @param <T>
	 * @param t
	 *            ��ѯ�Ķ���
	 * @param fieldName
	 *            �����Ķ���
	 * @return T
	 */
	public <T> T findLink(T t, String fieldName) {
		return dao.fetchLinks(t, fieldName);
	}

	/**
	 * ��������͹����Ķ���
	 * 
	 * @param <T>
	 * @param t
	 *            �޸ĵĶ���
	 * @param fieldName
	 *            ��������
	 * @return T
	 */
	public <T> T updateWidth(T t, String fieldName) {
		return dao.updateWith(t, fieldName);
	}

	/**
	 * ���޸Ĺ����Ķ��������
	 * 
	 * @param <T>
	 * @param t
	 *            ��ѯ����
	 * @param fieldName
	 *            �޸ĵĶ���
	 * @return T
	 */
	public <T> T updateLink(T t, String fieldName) {
		return dao.updateLinks(t, fieldName);
	}

	/**
	 * ɾ������͹�������
	 * 
	 * @param <T>
	 * @param t
	 *            ɾ���Ķ���
	 * @param fieldName
	 *            �����Ķ���
	 */
	public <T> void deleteWidth(T t, String fieldName) {
		dao.deleteWith(t, fieldName);
	}

	/**
	 * ɾ�������Ķ��󣬲�ɾ������
	 * 
	 * @param <T>
	 * @param t
	 *            ɾ��������
	 * @param fieldName
	 *            ɾ���Ĺ�������
	 */
	public <T> void deleteLink(T t, String fieldName) {
		dao.deleteLinks(t, fieldName);
	}

	/**
	 * �������Ķ�Զ� ��ϵ
	 * 
	 * @param <T>
	 * @param t
	 * @param fieldName
	 */
	public <T> T saveRelation(T t, String fieldName) {
		return dao.insertRelation(t, fieldName);
	}

	/**
	 * �������Ĺ�����ϵ,�����������
	 * 
	 * @param <T>
	 * @param t
	 * @param fieldName
	 * @return
	 */
	public <T> T saveLink(T t, String fieldName) {
		return dao.insertLinks(t, fieldName);
	}

	/**
	 * ���¶���Ķ�Զ��ϵ
	 * 
	 * @param <T>
	 * @param c
	 *            ���µĶ������
	 * @param fieldName
	 *            ���µ��ֶ�����
	 * @param chain
	 *            ���µ�����
	 * @param condition
	 *            ���µ�����
	 * @return true �ɹ�,false ʧ��
	 */
	public <T> boolean updateRelation(Class<T> c, String fieldName,
			Chain chain, Condition condition) {
		return dao.updateRelation(c, fieldName, chain, condition) > 0;
	}

	/**
	 * ���� '@One' �� '@Many'����Ӧ�ļ�¼����ɾ�� �� '@ManyMay' ��Ӧ���ֶΣ�ֻ������������еļ�¼
	 * 
	 * @param <T>
	 * @param t
	 * @param fieldName
	 * @return
	 */
	public <T> T clearRelation(T t, String fieldName) {
		return dao.clearLinks(t, fieldName);
	}

	/**
	 * �����м���ҳ��ѯ����
	 * 
	 * @param <T>
	 * @param c
	 *            ��ѯ����
	 * @param joinTabel
	 *            �м��
	 * @param cloumnName
	 *            Ҫ��ȡ�м����ֶ�
	 * @param condition
	 *            ��ѯ����
	 * @param group
	 *            ����ѯ������
	 * @param orderby
	 *            ����ʽ
	 * @param currentPage
	 *            ��ǰҳ��
	 * @param pageSize
	 *            ÿҳ��ʾ����
	 * @return
	 */
	public <T> List<T> searchByRelation(Class<T> c, String joinTabel,
			String cloumnName, Condition condition, SqlExpressionGroup group,
			String orderby, int currentPage, int pageSize) {
		Entity<T> entity = dao.getEntity(c);

		List<Record> records = dao.query(joinTabel, condition, null);

		List<Integer> ids = new ArrayList<Integer>();
		for (Record r : records) {
			int id = r.getInt(cloumnName);
			ids.add(id);
		}
		if (ids.size() == 0) {
			return null;
		}

		Pager pager = dao.createPager(currentPage, pageSize);

		SqlExpression e = Cnd.exp(entity.getIdField().getColumnName(), "in",
				Castors.me().castTo(ids, int[].class));

		ids = null;

		return dao.query(c, Cnd.where(group.and(e)).desc(orderby), pager);
	}

	/**
	 * ��ѯ���ݵ�����
	 * 
	 * @param <T>
	 * @param c
	 * @param joinTabel
	 *            �м��
	 * @param cloumnName
	 *            Ҫ��ȡ�м����ֶ�
	 * @param condition
	 *            ��ѯ����
	 * @param group
	 *            ����ѯ������
	 * @param orderby
	 *            ����ʽ
	 * @return
	 */
	public <T> int searchCount(Class<T> c, String joinTabel, String cloumnName,
			Condition condition, SqlExpressionGroup group, String orderby) {
		Entity<T> entity = dao.getEntity(c);

		List<Record> records = dao.query(joinTabel, condition, null);

		List<Integer> ids = new ArrayList<Integer>();
		for (Record r : records) {
			int id = r.getInt(cloumnName);
			ids.add(id);
		}
		if (ids.size() == 0) {
			return 0;
		}

		SqlExpression e = Cnd.exp(entity.getIdField().getColumnName(), "in",
				Castors.me().castTo(ids, int[].class));

		group = group.and(e);

		return dao.count(c, Cnd.where(group).desc(orderby));
	}

	public void delete(String table, Condition condition) {
		dao.clear(table, condition);
	}
}