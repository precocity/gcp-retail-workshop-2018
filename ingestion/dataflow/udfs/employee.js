function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.employee_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.employee_number = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.employee_first_name = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.employee_last_name = values[3].replace(/^"/, "").replace(/"$/, "");
	obj.employee_type = values[4].replace(/^"/, "").replace(/"$/, "");
	obj.employee_store_id = values[5].replace(/^"/, "").replace(/"$/, "");
	obj.employee_department_id = values[6].replace(/^"/, "").replace(/"$/, "");
	obj.employee_level = values[7].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[8].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[9].replace(/^"/, "").replace(/"$/, "");

	var jsonString = JSON.stringify(obj);

	return jsonString;
}