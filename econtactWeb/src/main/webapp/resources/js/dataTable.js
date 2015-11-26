function blurWithFilter(event, tableName) {
	if (event.keyCode == 13) {
		event.target.blur();
		PF(tableName).filter();
		return false;
	}
}