package com.econtact.authWeb.app.dataTable.model.employee;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.dataTable.model.AbstractGenericDataTableModel;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

public class EmployeesTableLazyModel extends AbstractGenericDataTableModel<AccountUserEntity>{
	private static final long serialVersionUID = 8380063193660660412L;

	public EmployeesTableLazyModel(DataTable table, UserContext userContext) {
		super(table, userContext);
	}

	@Override
	protected SearchCriteria<AccountUserEntity> createQueries() {
		SearchCriteria<AccountUserEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AccountUserEntity.class));
		criteria.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN))
				.andFilter(new FilterDefEquals(AccountUserEntity.PARENT_USER_A, getUserContext().getUser()));
		return criteria;
	}

	/**
	 * Запрос на рекурсивное дерево пользователей
	 * WITH RECURSIVE r AS (
   SELECT *
   FROM econtactschema.user_account
   WHERE id = 1 

   UNION ALL

   SELECT acc.*
   FROM econtactschema.user_account acc
      JOIN r
          ON acc.id_parent_user_fk = r.id
          AND acc.sign = 0
)

SELECT * FROM r;
	 */
}
