INSERT INTO econtactschema.univer_dict(
            id_univer_dict, version, abr_rec_dict, id_rec_dict, name_rec_dict, 
            param_dict, sign, upd_author, upd_date)
    VALUES (nextval('econtactschema.seq_univer_dict_id'), 0, 'подкл.', 1, 'Подключение', 
            'ACTION', 0, 'SYSTEM', CURRENT_DATE);

INSERT INTO econtactschema.univer_dict(
            id_univer_dict, version, abr_rec_dict, id_rec_dict, name_rec_dict, 
            param_dict, sign, upd_author, upd_date)
    VALUES (nextval('econtactschema.seq_univer_dict_id'), 0, 'откл.', 2, 'Отключение', 
            'ACTION', 0, 'SYSTEM', CURRENT_DATE);

INSERT INTO econtactschema.univer_dict(
            id_univer_dict, version, abr_rec_dict, id_rec_dict, name_rec_dict, 
            param_dict, sign, upd_author, upd_date)
    VALUES (nextval('econtactschema.seq_univer_dict_id'), 0, 'создание', 1, 'Создание', 
            'EVENT', 0, 'SYSTEM', CURRENT_DATE);

INSERT INTO econtactschema.univer_dict(
            id_univer_dict, version, abr_rec_dict, id_rec_dict, name_rec_dict, 
            param_dict, sign, upd_author, upd_date)
    VALUES (nextval('econtactschema.seq_univer_dict_id'), 0, 'обновление', 2, 'Обновление', 
            'EVENT', 0, 'SYSTEM', CURRENT_DATE);
INSERT INTO econtactschema.univer_dict(
            id_univer_dict, version, abr_rec_dict, id_rec_dict, name_rec_dict, 
            param_dict, sign, upd_author, upd_date)
    VALUES (nextval('econtactschema.seq_univer_dict_id'), 0, 'удаление', 3, 'Удаление', 
            'EVENT', 0, 'SYSTEM', CURRENT_DATE);
