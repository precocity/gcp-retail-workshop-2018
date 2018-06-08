function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.customer_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.customer_number = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.last_name = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.first_name = values[3].replace(/^"/, "").replace(/"$/, "");
	obj.alternate_lastName = values[4].replace(/^"/, "").replace(/"$/, "");
	obj.salutation = values[5].replace(/^"/, "").replace(/"$/, "");
	obj.birth_date = values[6].replace(/^"/, "").replace(/"$/, "");
	obj.gender = values[7].replace(/^"/, "").replace(/"$/, "");
	obj.marital_status = values[8].replace(/^"/, "").replace(/"$/, "");
	obj.anniversary = values[9].replace(/^"/, "").replace(/"$/, "");
	obj.primary_email = values[10].replace(/^"/, "").replace(/"$/, "");
	obj.secondary_email = values[11].replace(/^"/, "").replace(/"$/, "");
	obj.primary_phone = values[12].replace(/^"/, "").replace(/"$/, "");
	obj.primary_phone_type = values[13].replace(/^"/, "").replace(/"$/, "");
	obj.secondary_phone = values[14].replace(/^"/, "").replace(/"$/, "");
	obj.secondary_phone_type = values[15].replace(/^"/, "").replace(/"$/, "");
	obj.employee_flag = values[16] == "1" ? true : false;
	obj.social_handle_1 = values[17].replace(/^"/, "").replace(/"$/, "");
	obj.social_handle_1_type = values[18].replace(/^"/, "").replace(/"$/, "");
	obj.social_handle_2 = values[19].replace(/^"/, "").replace(/"$/, "");
	obj.social_handle_2_type = values[20].replace(/^"/, "").replace(/"$/, "");
	obj.household_id = values[21].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[22].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[23].replace(/^"/, "").replace(/"$/, "");

	var jsonString = JSON.stringify(obj);

	return jsonString;
}