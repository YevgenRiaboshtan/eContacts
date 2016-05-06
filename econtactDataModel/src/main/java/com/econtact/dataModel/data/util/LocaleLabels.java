package com.econtact.dataModel.data.util;

/**
 * Class represents keys of the localized messages used in the beans.
 * @author Yevgen Riaboshtan
 *
 */
public class LocaleLabels {
	/**
	 * Объект был изменен другим пользователем. Object was update by other user.
	 */
	public static final String OPTIMISTIC_LOCK_EXCEPTION_MESSAGE = "optimistic.lock.exception.message";
	/**
	 * Ошибка Error
	 */
	public static final String COMMON_ERROR_MESSAGE = "common.error.message";
	/**
	 * Предупреждение Warning
	 */
	public static final String COMMON_WARNING_MESSAGE = "common.warning.message";
	/**
	 * Информация Information
	 */
	public static final String COMMON_INFO_MESSAGE = "common.info.message";
	/**
	 * Актульно
	 * Actual
	 */
	public static final String SIGN_FILTER_ACTUAL = "sign.filter.actual";
	/**
	 * Удалено
	 * Deleted
	 */
	public static final String SIGN_FILTER_DELETE = "sign.filter.delete";

	/**
	 * Неверный пароль
	 */
	public static final String NEW_USER_WRONG_CURRENT_PASSWORD_ERROR_MESSAGE = "newUser.wrong.current.password.error.message";

	/**
	 * Выберите тип справочника
	 * Select paramDict
	 */
	public static final String DICTIONARY_SELECT_PARAM_DICT = "dictionary.select.paramDict";
	/**
	 * Тип контакта
	 * Contact type paramDict label for combobox.
	 */
	public static final String CONTACT_TYPE_UD_PARAM_DICT_LABEL = "dictionary.contact.type.paramDict";
	/**
	 * Возраст
	 * Person age range paramDict label for combobox.
	 */
	public static final String PERSON_AGE_RANGE_UD_PARAM_DICT_LABEL = "dictionary.person.age.range.paramDict";
	/**
	 * Статус
	 * Person status paramDict label for combobox.
	 */
	public static final String PERSON_STATUS_UD_PARAM_DICT_LABEL = "dictionary.person.status.paramDict";
	/**
	 * Пол
	 * Person sex paramDict label for combobox.
	 */
	public static final String PERSON_SEX_UD_PARAM_DICT_LABEL = "dictionary.person.sex.paramDict";
	/**
	 * Режим администрирования
	 * Select Admin mode on Select Church page
	 */
	public static final String SELECT_ADMIN_MODE = "selectChurch.admin.mode";
	/**
	 * Работа в {0} (Администратор {1})
	 * Select Normal mode on Select Church page
	 */
	public static final String SELECT_NORMAL_MODE = "selectChurch.normal.mode";

	/**
	 * Укажите Фамилию Имя или Отчество
	 * First, middle or last name must be write
	 */
	public static final String ADD_CONTACT_FIO_VALIDATION_MESSAGE = "addContact.fio.validate.message";
	
	private LocaleLabels() {
	};
}
