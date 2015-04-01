BEGIN TRANSACTION;

insert into reporting.report_category (report_category_id, description, abbreviation, active, created_on) values ('7a482694-51d2-42d0-b0e2-19dd13bbbc64', 'Accounting'       , 'ACCT' , false, '2015-04-30T12:00:00');
insert into reporting.report_category (report_category_id, description, abbreviation, active, created_on) values ('bb2bc482-c19a-4c19-a087-e68ffc62b5a0', 'Q-Free internal'  , 'QFREE', true , '2015-05-30T22:00:00');
insert into reporting.report_category (report_category_id, description, abbreviation, active, created_on) values ('5c3cc664-b685-4f6e-8d9a-2927c6bcffdc', 'Manual validation', 'MIR'  , false, '2015-03-01T02:00:00');
insert into reporting.report_category (report_category_id, description, abbreviation, active, created_on) values ('72d7cb27-1770-4cc7-b301-44d39ccf1e76', 'Traffic'          , 'TRA'  , true , '2015-03-01T02:00:10');

CREATE TEMPORARY TABLE tmp_rptdesign (rptdesign text) ON COMMIT DROP;
INSERT INTO tmp_rptdesign (rptdesign) VALUES ('<?xml version="1.0" encoding="UTF-8"?>
<report xmlns="http://www.eclipse.org/birt/2005/design" version="3.2.23" id="1">
    <property name="createdBy">Eclipse BIRT Designer Version 4.4.2.v201410272105 Build &lt;4.4.2.v20150217-1805></property>
    <property name="units">in</property>
    <property name="iconFile">/templates/blank_report.gif</property>
    <property name="bidiLayoutOrientation">ltr</property>
    <property name="imageDPI">96</property>
    <page-setup>
        <simple-master-page name="Simple MasterPage" id="2"/>
    </page-setup>
</report>');

insert into reporting.report (report_id, report_category_id, name, created_on, rptdesign) values ('d65f3d9c-f67d-4beb-9936-9dfa19aa1407', '7a482694-51d2-42d0-b0e2-19dd13bbbc64', 'Report name #01', '2014-06-09T22:00:00', (SELECT rptdesign FROM tmp_rptdesign));
insert into reporting.report (report_id, report_category_id, name, created_on, rptdesign) values ('c7f1d394-9814-4ede-bb01-2700187d79ca', '7a482694-51d2-42d0-b0e2-19dd13bbbc64', 'Report name #02', '2014-06-09T22:10:00', (SELECT rptdesign FROM tmp_rptdesign));
insert into reporting.report (report_id, report_category_id, name, created_on, rptdesign) values ('fe718314-5b39-40e7-aed2-279354c04a9d', '7a482694-51d2-42d0-b0e2-19dd13bbbc64', 'Report name #03', '2014-07-04T23:30:00', (SELECT rptdesign FROM tmp_rptdesign));
insert into reporting.report (report_id, report_category_id, name, created_on, rptdesign) values ('702d5daa-e23d-4f00-b32b-67b44c06d8f6', 'bb2bc482-c19a-4c19-a087-e68ffc62b5a0', 'Report name #04', '2014-03-25T12:15:00', (SELECT rptdesign FROM tmp_rptdesign));
insert into reporting.report (report_id, report_category_id, name, created_on, rptdesign) values ('f1f06b15-c0b6-488d-9eed-74e867a47d5a', '72d7cb27-1770-4cc7-b301-44d39ccf1e76', 'Report name #05', '2014-03-25T12:15:00', (SELECT rptdesign FROM tmp_rptdesign));
insert into reporting.report (report_id, report_category_id, name, created_on, rptdesign) values ('adc50b28-cb84-4ede-9759-43f467ac22ec', 'bb2bc482-c19a-4c19-a087-e68ffc62b5a0', 'Report name #06', '2015-05-06T15:00:00', (SELECT rptdesign FROM tmp_rptdesign));

insert into reporting.widget (widget_id, name, description, multiple_select ,active, created_on ) values ('b8e91527-8b0e-4ed2-8cba-8cb8989ba8e2', 'widget #1', 'widget #1 description', false, true, '2015-03-31T02:00:00');

insert into reporting.report_parameter (report_parameter_id, report_id, name, description, widget_id, required, created_on) values ('206723d6-50e7-4f4a-85c0-cb679e92ad6b', 'd65f3d9c-f67d-4beb-9936-9dfa19aa1407', 'Report01Param01', 'Parameter #1 for Report #1', 'b8e91527-8b0e-4ed2-8cba-8cb8989ba8e2', true,  '2015-05-06T15:00:00');
insert into reporting.report_parameter (report_parameter_id, report_id, name, description, widget_id, required, created_on) values ('36fc0de4-cc4c-4efa-8c47-73e0e254e449', 'c7f1d394-9814-4ede-bb01-2700187d79ca', 'Report02Param01', 'Parameter #1 for Report #2', 'b8e91527-8b0e-4ed2-8cba-8cb8989ba8e2', false, '2015-05-06T15:00:01');
insert into reporting.report_parameter (report_parameter_id, report_id, name, description, widget_id, required, created_on) values ('5a201251-b04f-406e-b07c-c6d55dc3dc85', 'fe718314-5b39-40e7-aed2-279354c04a9d', 'Report03Param01', 'Parameter #1 for Report #3', 'b8e91527-8b0e-4ed2-8cba-8cb8989ba8e2', true,  '2015-05-06T15:00:02');
insert into reporting.report_parameter (report_parameter_id, report_id, name, description, widget_id, required, created_on) values ('4c2bd07e-c7d7-451a-8c07-c8f589959382', '702d5daa-e23d-4f00-b32b-67b44c06d8f6', 'Report04Param01', 'Parameter #1 for Report #4', 'b8e91527-8b0e-4ed2-8cba-8cb8989ba8e2', true,  '2015-05-06T15:00:03');
insert into reporting.report_parameter (report_parameter_id, report_id, name, description, widget_id, required, created_on) values ('73792710-8d69-477a-8d44-fe646507eaf8', 'f1f06b15-c0b6-488d-9eed-74e867a47d5a', 'Report05Param01', 'Parameter #1 for Report #5', 'b8e91527-8b0e-4ed2-8cba-8cb8989ba8e2', true,  '2015-05-06T15:00:04');
insert into reporting.report_parameter (report_parameter_id, report_id, name, description, widget_id, required, created_on) values ('86e93f08-86fd-4aed-99c2-1f4af29382d3', 'adc50b28-cb84-4ede-9759-43f467ac22ec', 'Report06Param01', 'Parameter #1 for Report #6', 'b8e91527-8b0e-4ed2-8cba-8cb8989ba8e2', false, '2015-05-06T15:00:05');


-- Insert  tree of [role] records:

INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('e73ee6a5-5236-4630-aba1-de18e76b8105', 'a', false, '', '', '2015-04-13T08:00:00');

-- Children of "a":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('1f47643b-0cb7-42a1-82bd-ab912d369567', 'aa', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('07d768c2-9243-4521-bf31-53cfe7a54eb7', 'ab', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('e745b03b-a63d-4c14-8b3c-d9aa773080f1', 'ac', false, '', '', '2015-04-13T08:00:00');
-- parent='a', child='aa':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('d8654af5-c855-4eae-9ec9-4358b46c07bc', 'e73ee6a5-5236-4630-aba1-de18e76b8105', '1f47643b-0cb7-42a1-82bd-ab912d369567', '2015-04-13T09:00:00');
-- parent='a', child='ab':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('0cfe9f63-1916-4d2a-bff8-ffeddf0243cc', 'e73ee6a5-5236-4630-aba1-de18e76b8105', '07d768c2-9243-4521-bf31-53cfe7a54eb7', '2015-04-13T09:00:00');
-- parent='a', child='ac':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('a71d5e9f-752e-4e92-936e-71d7276da511', 'e73ee6a5-5236-4630-aba1-de18e76b8105', 'e745b03b-a63d-4c14-8b3c-d9aa773080f1', '2015-04-13T09:00:00');

-- Children of "aa":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('1994c086-7d8d-47c0-ab1d-5bfa7e8d0267', 'aaa', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('149c594e-9ae6-4c48-b02d-e84659d030ad', 'aab', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('63c8df84-8eec-4703-944c-a3819accb707', 'aac', false, '', '', '2015-04-13T08:00:00');
-- parent='aa', child='aaa':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('a464e97e-40a5-4c6b-b465-98e7b794c00d', '1f47643b-0cb7-42a1-82bd-ab912d369567', '1994c086-7d8d-47c0-ab1d-5bfa7e8d0267', '2015-04-13T09:00:00');
-- parent='aa', child='aab':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('c9854904-2179-4464-8bc5-04d1ee8e8507', '1f47643b-0cb7-42a1-82bd-ab912d369567', '149c594e-9ae6-4c48-b02d-e84659d030ad', '2015-04-13T09:00:00');
-- parent='aa', child='aac':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('336ef842-9ebe-4064-9b09-3ae9881fc5ef', '1f47643b-0cb7-42a1-82bd-ab912d369567', '63c8df84-8eec-4703-944c-a3819accb707', '2015-04-13T09:00:00');

-- Children of "ab":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('36078747-e2fe-4f2e-9545-df87f8edc2f9', 'aba', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('39adb1d3-098f-4a56-9563-14a65b750c27', 'abb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('6acb9991-30ea-4358-9f77-4a06cabefe1f', 'abc', false, '', '', '2015-04-13T08:00:00');
-- parent='ab', child='aba':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('d1f98324-58f4-45f4-8208-321deda15fcf', '07d768c2-9243-4521-bf31-53cfe7a54eb7', '36078747-e2fe-4f2e-9545-df87f8edc2f9', '2015-04-13T09:00:00');
-- parent='ab', child='abb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('480b78e0-6c4a-4924-b94a-699800bdbc5d', '07d768c2-9243-4521-bf31-53cfe7a54eb7', '39adb1d3-098f-4a56-9563-14a65b750c27', '2015-04-13T09:00:00');
-- parent='ab', child='abc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('59b85cd4-3f83-4e09-bb5a-c1ea3d45fbd4', '07d768c2-9243-4521-bf31-53cfe7a54eb7', '6acb9991-30ea-4358-9f77-4a06cabefe1f', '2015-04-13T09:00:00');

-- Children of "ac":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('35b9a821-235a-413f-b76a-d0fa228e122a', 'aca', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('475242f1-3377-4ead-ba89-ea5eff04b559', 'acb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('4fcf016c-d30a-4d50-b7b1-4bd8d4fdc5ae', 'acc', false, '', '', '2015-04-13T08:00:00');
-- parent='ac', child='aca':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('e969d37e-5449-47be-b58e-9c4157ddea3c', 'e745b03b-a63d-4c14-8b3c-d9aa773080f1', '35b9a821-235a-413f-b76a-d0fa228e122a', '2015-04-13T09:00:00');
-- parent='ac', child='acb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('4f771a9c-1018-4a9e-af37-3b3c344b9353', 'e745b03b-a63d-4c14-8b3c-d9aa773080f1', '475242f1-3377-4ead-ba89-ea5eff04b559', '2015-04-13T09:00:00');
-- parent='ac', child='acc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('15a7727c-c36d-472f-8f29-6012f5b1a227', 'e745b03b-a63d-4c14-8b3c-d9aa773080f1', '4fcf016c-d30a-4d50-b7b1-4bd8d4fdc5ae', '2015-04-13T09:00:00');

-- Children of "aaa":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('6c328253-5fa6-4b11-8052-ef38197931b0', 'aaaa', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('9fe6532b-2f52-4f8e-ace1-f7165dccbd5f', 'aaab', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('2326720a-adda-47f1-a68e-53a66112be34', 'aaac', false, '', '', '2015-04-13T08:00:00');
-- parent='aaa', child='aaaa':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('70a3da53-11bb-4951-8bde-33561e0c9ac5', '1994c086-7d8d-47c0-ab1d-5bfa7e8d0267', '6c328253-5fa6-4b11-8052-ef38197931b0', '2015-04-13T09:00:00');
-- parent='aaa', child='aaab':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('c3081aff-06b7-4c6e-9139-af17d7755ccb', '1994c086-7d8d-47c0-ab1d-5bfa7e8d0267', '9fe6532b-2f52-4f8e-ace1-f7165dccbd5f', '2015-04-13T09:00:00');
-- parent='aaa', child='aaac':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('c2b733de-253f-4ddd-bc47-36cd9074a990', '1994c086-7d8d-47c0-ab1d-5bfa7e8d0267', '2326720a-adda-47f1-a68e-53a66112be34', '2015-04-13T09:00:00');

-- Children of "aab":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('dcbf65cb-39a3-44bd-a2ef-802c451497be', 'aaba', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('ee56f34d-dbb4-41c1-9d30-ce29cf973820', 'aabb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('f46f6b53-d70e-4044-91e2-ff749159fd90', 'aabc', false, '', '', '2015-04-13T08:00:00');
-- parent='aab', child='aaba':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('9c43849b-087b-42ed-b034-9149d421891c', '149c594e-9ae6-4c48-b02d-e84659d030ad', 'dcbf65cb-39a3-44bd-a2ef-802c451497be', '2015-04-13T09:00:00');
-- parent='aab', child='aabb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('c4ecb51b-494e-4b55-a8b1-2f47abb2935e', '149c594e-9ae6-4c48-b02d-e84659d030ad', 'ee56f34d-dbb4-41c1-9d30-ce29cf973820', '2015-04-13T09:00:00');
-- parent='aab', child='aabc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('0234b8b7-6d88-4218-ab5e-4593d03d9bd8', '149c594e-9ae6-4c48-b02d-e84659d030ad', 'f46f6b53-d70e-4044-91e2-ff749159fd90', '2015-04-13T09:00:00');

-- Children of "aac":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('83f93a4b-473c-4af8-b79a-7796fa04e1f9', 'aaca', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('82f54907-c95e-4a10-8266-e68ad4da6dde', 'aacb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('b7908014-c7ee-4f34-8073-8e1016d620e7', 'aacc', false, '', '', '2015-04-13T08:00:00');
-- parent='aac', child='aaca':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('b73bd9bc-354d-49f6-a980-98a43cf79e5e', '63c8df84-8eec-4703-944c-a3819accb707', '83f93a4b-473c-4af8-b79a-7796fa04e1f9', '2015-04-13T09:00:00');
-- parent='aac', child='aacb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('66a80ec4-e016-4d3f-9104-1c013ed9ab00', '63c8df84-8eec-4703-944c-a3819accb707', '82f54907-c95e-4a10-8266-e68ad4da6dde', '2015-04-13T09:00:00');
-- parent='aac', child='aacc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('68499541-35fc-4c72-a795-42e5ec62caf0', '63c8df84-8eec-4703-944c-a3819accb707', 'b7908014-c7ee-4f34-8073-8e1016d620e7', '2015-04-13T09:00:00');

-- Children of "aba":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('2449f7e0-65d9-4c4a-b675-ec4cafc061a2', 'abaa', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('cfef7ad6-3952-4832-9213-01e1033239ea', 'abab', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('c93da9e8-ba8d-4ac4-aed0-648955c0a317', 'abac', false, '', '', '2015-04-13T08:00:00');
-- parent='aba', child='abaa':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('865c7e98-207b-4068-a007-5bf4f5c81871', '36078747-e2fe-4f2e-9545-df87f8edc2f9', '2449f7e0-65d9-4c4a-b675-ec4cafc061a2', '2015-04-13T09:00:00');
-- parent='aba', child='abab':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('66affde3-adc4-4e24-9448-de7759226d53', '36078747-e2fe-4f2e-9545-df87f8edc2f9', 'cfef7ad6-3952-4832-9213-01e1033239ea', '2015-04-13T09:00:00');
-- parent='aba', child='abac':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('e49ab801-ab98-4f37-9917-e12536d3c94c', '36078747-e2fe-4f2e-9545-df87f8edc2f9', 'c93da9e8-ba8d-4ac4-aed0-648955c0a317', '2015-04-13T09:00:00');

-- Children of "abb":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('c56ec66e-e2e4-4e21-9bb8-5dacd8d4c303', 'abba', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('85f2778d-9c4e-4018-9014-ca02fd558bcb', 'abbb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('12ff9c46-d3ab-4e5e-a00f-f1acde700256', 'abbc', false, '', '', '2015-04-13T08:00:00');
-- parent='abb', child='abba':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('34684b2f-0919-4fe8-8e51-1ba7a92c8526', '39adb1d3-098f-4a56-9563-14a65b750c27', 'c56ec66e-e2e4-4e21-9bb8-5dacd8d4c303', '2015-04-13T09:00:00');
-- parent='abb', child='abbb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('76d38d4f-aac8-4416-b9fe-1cae2cbcbf56', '39adb1d3-098f-4a56-9563-14a65b750c27', '85f2778d-9c4e-4018-9014-ca02fd558bcb', '2015-04-13T09:00:00');
-- parent='abb', child='abbc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('2680a894-c2a9-4c13-9689-7fe3263e4fb4', '39adb1d3-098f-4a56-9563-14a65b750c27', '12ff9c46-d3ab-4e5e-a00f-f1acde700256', '2015-04-13T09:00:00');

-- Children of "abc":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('632079ee-1255-4b35-93b1-aeb30bfab97a', 'abca', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('1163cc14-2edd-4f05-a9d8-f4dbee84ce47', 'abcb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('ae37d825-09dd-48fa-95d4-eb37d7fd0d5b', 'abcc', false, '', '', '2015-04-13T08:00:00');
-- parent='abc', child='abca':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('d4151504-b5b5-4ae8-b7c5-1837e4ffbc5c', '6acb9991-30ea-4358-9f77-4a06cabefe1f', '632079ee-1255-4b35-93b1-aeb30bfab97a', '2015-04-13T09:00:00');
-- parent='abc', child='abcb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('ac22ec35-f719-4327-9b09-3cfe57e24095', '6acb9991-30ea-4358-9f77-4a06cabefe1f', '1163cc14-2edd-4f05-a9d8-f4dbee84ce47', '2015-04-13T09:00:00');
-- parent='abc', child='abcc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('0c7653c5-90c1-4878-a0e4-3315e2f7429d', '6acb9991-30ea-4358-9f77-4a06cabefe1f', 'ae37d825-09dd-48fa-95d4-eb37d7fd0d5b', '2015-04-13T09:00:00');

-- Children of "aca":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('a6336c72-9d01-49f8-966d-fbef3822a735', 'acaa', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('7ad44167-6dd9-46eb-a613-69c1e31e51d6', 'acab', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('23a0a9c3-921a-4abd-bd03-7ea85492ec00', 'acac', false, '', '', '2015-04-13T08:00:00');
-- parent='aca', child='acaa':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('61e4485e-f6b9-47e6-a276-72a6b689a33b', '35b9a821-235a-413f-b76a-d0fa228e122a', 'a6336c72-9d01-49f8-966d-fbef3822a735', '2015-04-13T09:00:00');
-- parent='aca', child='acab':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('88e9c6a5-650b-4d87-8088-9eeadd77cb64', '35b9a821-235a-413f-b76a-d0fa228e122a', '7ad44167-6dd9-46eb-a613-69c1e31e51d6', '2015-04-13T09:00:00');
-- parent='aca', child='acac':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('62cf90fe-1e9c-40f2-8bfb-7d81beccae96', '35b9a821-235a-413f-b76a-d0fa228e122a', '23a0a9c3-921a-4abd-bd03-7ea85492ec00', '2015-04-13T09:00:00');

-- Children of "acb":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('51c75a7f-ad96-457d-b170-570bb88ccc49', 'acba', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('b165daef-af52-42e5-9f46-48f5190d0130', 'acbb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('f7be0917-5760-4969-a048-377a02d962e4', 'acbc', false, '', '', '2015-04-13T08:00:00');
-- parent='acb', child='acba':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('abb6bf6c-3136-4185-95f3-d91441b299ab', '475242f1-3377-4ead-ba89-ea5eff04b559', '51c75a7f-ad96-457d-b170-570bb88ccc49', '2015-04-13T09:00:00');
-- parent='acb', child='acbb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('5e7bd676-c8bc-4d49-b8e5-4ab285411a04', '475242f1-3377-4ead-ba89-ea5eff04b559', 'b165daef-af52-42e5-9f46-48f5190d0130', '2015-04-13T09:00:00');
-- parent='acb', child='acbc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('777c3282-a755-4446-a463-9304c69fc873', '475242f1-3377-4ead-ba89-ea5eff04b559', 'f7be0917-5760-4969-a048-377a02d962e4', '2015-04-13T09:00:00');

-- Children of "acc":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('39bc8737-b9eb-4cb5-9765-1c33dd5ee40c', 'acca', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('8efa8e84-3dbb-4dc1-b6a6-1f97d0b2f9c5', 'accb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('112bd37f-247a-47b2-a658-e11f652e6051', 'accc', false, '', '', '2015-04-13T08:00:00');
-- parent='acc', child='acca':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('d8df82c9-f95a-482a-89dc-b94a46728ad6', '4fcf016c-d30a-4d50-b7b1-4bd8d4fdc5ae', '39bc8737-b9eb-4cb5-9765-1c33dd5ee40c', '2015-04-13T09:00:00');
-- parent='acc', child='accb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('b6431df1-15ec-492f-90af-d0cbe8e33ad8', '4fcf016c-d30a-4d50-b7b1-4bd8d4fdc5ae', '8efa8e84-3dbb-4dc1-b6a6-1f97d0b2f9c5', '2015-04-13T09:00:00');
-- parent='acc', child='accc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('cbc9351f-235a-4565-81ac-ba8a6c3e1c2a', '4fcf016c-d30a-4d50-b7b1-4bd8d4fdc5ae', '112bd37f-247a-47b2-a658-e11f652e6051', '2015-04-13T09:00:00');


INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('4503c055-5eb2-4d51-8424-2379843b4492', 'b', false, '', '', '2015-04-13T08:00:00');

-- Children of "b":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('c8662604-d446-4b46-bfbf-cd785b43e574', 'ba', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('7ddd187d-d0c8-4554-b379-a870f2731839', 'bb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('b60449da-db2d-43e4-9d4b-7592895fc666', 'bc', false, '', '', '2015-04-13T08:00:00');
-- parent='b', child='ba':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('d1885b4e-4d94-4875-938a-b54b0a2ac495', '4503c055-5eb2-4d51-8424-2379843b4492', 'c8662604-d446-4b46-bfbf-cd785b43e574', '2015-04-13T09:00:00');
-- parent='b', child='bb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('5cd13597-42a9-4fe1-a8ea-10df381903a2', '4503c055-5eb2-4d51-8424-2379843b4492', '7ddd187d-d0c8-4554-b379-a870f2731839', '2015-04-13T09:00:00');
-- parent='b', child='bc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('ad5a44d6-4b38-4f32-9ebd-15ab83eacbef', '4503c055-5eb2-4d51-8424-2379843b4492', 'b60449da-db2d-43e4-9d4b-7592895fc666', '2015-04-13T09:00:00');

-- Children of "ba":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('038f1b23-8470-44ae-bdd8-ed74653b77ad', 'baa', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('1fd6d260-c504-4a32-acc9-458f78381ae3', 'bab', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('f5287f17-3a73-413b-b91b-76d8d65b6ad9', 'bac', false, '', '', '2015-04-13T08:00:00');
-- parent='ba', child='baa':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('92fb4943-82e5-4114-894a-a422028a8cc7', 'c8662604-d446-4b46-bfbf-cd785b43e574', '038f1b23-8470-44ae-bdd8-ed74653b77ad', '2015-04-13T09:00:00');
-- parent='ba', child='bab':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('85ad36b9-19a0-4d98-9f2e-41e51cc071c5', 'c8662604-d446-4b46-bfbf-cd785b43e574', '1fd6d260-c504-4a32-acc9-458f78381ae3', '2015-04-13T09:00:00');
-- parent='ba', child='bac':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('2120d1da-98ea-4cd8-89e3-ff32ee188426', 'c8662604-d446-4b46-bfbf-cd785b43e574', 'f5287f17-3a73-413b-b91b-76d8d65b6ad9', '2015-04-13T09:00:00');

-- Children of "bb":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('2d64dc16-dae1-4159-90e1-f90356dc5d76', 'bba', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('984b9a34-411b-4fa3-86f0-d7cc83991c6d', 'bbb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('f9df30b3-2424-428d-94fc-ae95e4f64e3a', 'bbc', false, '', '', '2015-04-13T08:00:00');
-- parent='bb', child='bba':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('c53298c4-05a1-4dbb-a448-bce5111523f1', '7ddd187d-d0c8-4554-b379-a870f2731839', '2d64dc16-dae1-4159-90e1-f90356dc5d76', '2015-04-13T09:00:00');
-- parent='bb', child='bbb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('ac999609-ddbe-4b19-926c-d0d0604a1bf1', '7ddd187d-d0c8-4554-b379-a870f2731839', '984b9a34-411b-4fa3-86f0-d7cc83991c6d', '2015-04-13T09:00:00');
-- parent='bb', child='bbc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('536fc578-d6d8-40b9-adff-65f519cbaa9e', '7ddd187d-d0c8-4554-b379-a870f2731839', 'f9df30b3-2424-428d-94fc-ae95e4f64e3a', '2015-04-13T09:00:00');

-- Children of "bc":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('ee9767e3-bd6f-4acb-85e0-03b469809be1', 'bca', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('731122e9-0db8-48b3-a70e-fab985b63cf4', 'bcb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('69f6b032-3989-4b06-be04-0e4bf5536997', 'bcc', false, '', '', '2015-04-13T08:00:00');
-- parent='bc', child='bca':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('8b9651b1-c130-418e-84e9-d264025d2020', 'b60449da-db2d-43e4-9d4b-7592895fc666', 'ee9767e3-bd6f-4acb-85e0-03b469809be1', '2015-04-13T09:00:00');
-- parent='bc', child='bcb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('b2c7d5da-c388-4685-b4a7-d9c81d3017fd', 'b60449da-db2d-43e4-9d4b-7592895fc666', '731122e9-0db8-48b3-a70e-fab985b63cf4', '2015-04-13T09:00:00');
-- parent='bc', child='bcc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('41ac48b6-7cbc-425b-ba39-3434f5eaadc3', 'b60449da-db2d-43e4-9d4b-7592895fc666', '69f6b032-3989-4b06-be04-0e4bf5536997', '2015-04-13T09:00:00');

-- Children of "baa":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('0db97c2a-fb78-464a-a0e7-8d25f6003c14', 'baaa', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('fb506ceb-f1b9-422f-b41a-646c2f530256', 'baab', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('3774cf4a-4b0c-4576-b9e6-d834696db84c', 'baac', false, '', '', '2015-04-13T08:00:00');
-- parent='baa', child='baaa':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('230fd828-035d-429c-b245-c0607265f988', '038f1b23-8470-44ae-bdd8-ed74653b77ad', '0db97c2a-fb78-464a-a0e7-8d25f6003c14', '2015-04-13T09:00:00');
-- parent='baa', child='baab':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('7adb482a-45aa-4be6-9b98-03c1ef3d178f', '038f1b23-8470-44ae-bdd8-ed74653b77ad', 'fb506ceb-f1b9-422f-b41a-646c2f530256', '2015-04-13T09:00:00');
-- parent='baa', child='baac':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('4e7bd354-7f89-43ce-ba74-f1b349c8d2a3', '038f1b23-8470-44ae-bdd8-ed74653b77ad', '3774cf4a-4b0c-4576-b9e6-d834696db84c', '2015-04-13T09:00:00');

-- Children of "bab":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('ca3c0eec-2993-47b9-a0a2-5f3b831ac546', 'baba', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('d75593c1-47b9-403a-9e37-a99ba377fb0d', 'babb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('c75ebd4f-1c39-4fe7-9a20-2789d580079e', 'babc', false, '', '', '2015-04-13T08:00:00');
-- parent='bab', child='baba':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('5ea0444d-a908-4ff8-a320-6561f7f17201', '1fd6d260-c504-4a32-acc9-458f78381ae3', 'ca3c0eec-2993-47b9-a0a2-5f3b831ac546', '2015-04-13T09:00:00');
-- parent='bab', child='babb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('316f88cc-8646-4f51-9112-8f2a72f48399', '1fd6d260-c504-4a32-acc9-458f78381ae3', 'd75593c1-47b9-403a-9e37-a99ba377fb0d', '2015-04-13T09:00:00');
-- parent='bab', child='babc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('3b139c0c-564c-42ac-a77e-fe2913c55b9f', '1fd6d260-c504-4a32-acc9-458f78381ae3', 'c75ebd4f-1c39-4fe7-9a20-2789d580079e', '2015-04-13T09:00:00');

-- Children of "bac":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('1db588f6-b0af-4fd5-8394-0ad23480eece', 'baca', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('4ee5d656-34a0-4e81-8d1c-1d615861fb3e', 'bacb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('0c659cba-53a5-4015-aaf3-473e469ed6ce', 'bacc', false, '', '', '2015-04-13T08:00:00');
-- parent='bac', child='baca':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('e3a1d323-e79e-457b-a3c9-2c3ad4d34166', 'f5287f17-3a73-413b-b91b-76d8d65b6ad9', '1db588f6-b0af-4fd5-8394-0ad23480eece', '2015-04-13T09:00:00');
-- parent='bac', child='bacb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('7df4ba4c-c39b-4fae-b965-00c78fce1b48', 'f5287f17-3a73-413b-b91b-76d8d65b6ad9', '4ee5d656-34a0-4e81-8d1c-1d615861fb3e', '2015-04-13T09:00:00');
-- parent='bac', child='bacc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('11f3de93-973c-498d-a465-764906cca882', 'f5287f17-3a73-413b-b91b-76d8d65b6ad9', '0c659cba-53a5-4015-aaf3-473e469ed6ce', '2015-04-13T09:00:00');

-- Children of "bba":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('82fa32fe-96e9-44ce-961d-0d47c17a26f5', 'bbaa', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('9e873c4e-3cfd-4db0-b443-631781408f83', 'bbab', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('99bac641-9de9-4d5d-8b48-36cd071407b9', 'bbac', false, '', '', '2015-04-13T08:00:00');
-- parent='bba', child='bbaa':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('cce554db-1cd8-4182-bb98-c424214854e0', '2d64dc16-dae1-4159-90e1-f90356dc5d76', '82fa32fe-96e9-44ce-961d-0d47c17a26f5', '2015-04-13T09:00:00');
-- parent='bba', child='bbab':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('6541111d-ab28-4e11-8d46-63ce276eaded', '2d64dc16-dae1-4159-90e1-f90356dc5d76', '9e873c4e-3cfd-4db0-b443-631781408f83', '2015-04-13T09:00:00');
-- parent='bba', child='bbac':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('f1e8d631-7a40-4943-ab1a-b60495dc04f0', '2d64dc16-dae1-4159-90e1-f90356dc5d76', '99bac641-9de9-4d5d-8b48-36cd071407b9', '2015-04-13T09:00:00');

-- Children of "bbb":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('4f126264-e8b8-4a42-9152-52d8d375aa9c', 'bbba', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('e8f94ddf-6b90-43a6-a832-b7c07da02e89', 'bbbb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('31fdb345-0286-4a6d-9c4d-bae2bd12ce3b', 'bbbc', false, '', '', '2015-04-13T08:00:00');
-- parent='bbb', child='bbba':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('97d4fe98-80cc-4a4e-bc4b-afd2668d2f7b', '984b9a34-411b-4fa3-86f0-d7cc83991c6d', '4f126264-e8b8-4a42-9152-52d8d375aa9c', '2015-04-13T09:00:00');
-- parent='bbb', child='bbbb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('ea1e1e23-737b-4a9e-a704-50ccf6a77f43', '984b9a34-411b-4fa3-86f0-d7cc83991c6d', 'e8f94ddf-6b90-43a6-a832-b7c07da02e89', '2015-04-13T09:00:00');
-- parent='bbb', child='bbbc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('8944fbd2-08d9-4fa7-8da8-6842f57ce52b', '984b9a34-411b-4fa3-86f0-d7cc83991c6d', '31fdb345-0286-4a6d-9c4d-bae2bd12ce3b', '2015-04-13T09:00:00');

-- Children of "bbc":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('36d53b39-4fcf-4000-b31d-803fb9238236', 'bbca', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('82e145b7-225d-4193-a6a7-284658c1bb69', 'bbcb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('e103f734-7869-4e7e-a8c4-3d0bc5895c34', 'bbcc', false, '', '', '2015-04-13T08:00:00');
-- parent='bbc', child='bbca':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('77bd824e-51c6-43f9-807e-3169aebe8c9a', 'f9df30b3-2424-428d-94fc-ae95e4f64e3a', '36d53b39-4fcf-4000-b31d-803fb9238236', '2015-04-13T09:00:00');
-- parent='bbc', child='bbcb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('7743df46-1fe0-44bf-aa1d-108ba7e3f42d', 'f9df30b3-2424-428d-94fc-ae95e4f64e3a', '82e145b7-225d-4193-a6a7-284658c1bb69', '2015-04-13T09:00:00');
-- parent='bbc', child='bbcc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('eeaa2d49-08f6-4eb1-b949-f71e51236409', 'f9df30b3-2424-428d-94fc-ae95e4f64e3a', 'e103f734-7869-4e7e-a8c4-3d0bc5895c34', '2015-04-13T09:00:00');

-- Children of "bca":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('ab79b066-1082-46a6-92c2-4454d9241d48', 'bcaa', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('d0c3bbc0-3719-40cf-91f3-c04dc92b43be', 'bcab', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('7f8dab88-c6db-40be-a249-0f7d673a817f', 'bcac', false, '', '', '2015-04-13T08:00:00');
-- parent='bca', child='bcaa':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('09bd5e8a-bb01-4a94-9a32-200704c52d28', 'ee9767e3-bd6f-4acb-85e0-03b469809be1', 'ab79b066-1082-46a6-92c2-4454d9241d48', '2015-04-13T09:00:00');
-- parent='bca', child='bcab':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('3dbbcf07-a054-4460-a452-6466479c760f', 'ee9767e3-bd6f-4acb-85e0-03b469809be1', 'd0c3bbc0-3719-40cf-91f3-c04dc92b43be', '2015-04-13T09:00:00');
-- parent='bca', child='bcac':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('58b3240c-7663-4fca-bd2e-c91be6bed503', 'ee9767e3-bd6f-4acb-85e0-03b469809be1', '7f8dab88-c6db-40be-a249-0f7d673a817f', '2015-04-13T09:00:00');

-- Children of "bcb":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('11c0e37f-a623-4838-a8cf-ac35970e1c1d', 'bcba', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('31ed24f4-5d4a-4703-9b62-af877755ce90', 'bcbb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('e918c8aa-c6d1-462c-9e91-f1db0fb9f346', 'bcbc', false, '', '', '2015-04-13T08:00:00');
-- parent='bcb', child='bcba':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('7a221f59-10a3-4730-a5a3-e27259f4d65d', '731122e9-0db8-48b3-a70e-fab985b63cf4', '11c0e37f-a623-4838-a8cf-ac35970e1c1d', '2015-04-13T09:00:00');
-- parent='bcb', child='bcbb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('eb58c1cc-594d-4d3b-964e-2addf8d790c6', '731122e9-0db8-48b3-a70e-fab985b63cf4', '31ed24f4-5d4a-4703-9b62-af877755ce90', '2015-04-13T09:00:00');
-- parent='bcb', child='bcbc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('6c266fe3-b3e0-46a4-ae8d-f28e7a71f3bb', '731122e9-0db8-48b3-a70e-fab985b63cf4', 'e918c8aa-c6d1-462c-9e91-f1db0fb9f346', '2015-04-13T09:00:00');

-- Children of "bcc":
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('685fdc29-c30a-40c7-9aac-1553b53a1a10', 'bcca', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('ecf3be61-7bb3-4e05-8491-9dfcbf57493b', 'bccb', false, '', '', '2015-04-13T08:00:00');
INSERT INTO role (role_id, username, login_role, encoded_password, full_name, created_on) VALUES ('6b8ec23a-0bde-4d77-98df-0ed0b763ba69', 'bccc', false, '', '', '2015-04-13T08:00:00');
-- parent='bcc', child='bcca':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('575c1944-45a1-4528-a5ad-241b214a3578', '69f6b032-3989-4b06-be04-0e4bf5536997', '685fdc29-c30a-40c7-9aac-1553b53a1a10', '2015-04-13T09:00:00');
-- parent='bcc', child='bccb':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('fa17f095-624c-455b-9b69-e38bd3e1ff0f', '69f6b032-3989-4b06-be04-0e4bf5536997', 'ecf3be61-7bb3-4e05-8491-9dfcbf57493b', '2015-04-13T09:00:00');
-- parent='bcc', child='bccc':
INSERT INTO role_role (role_role_id, parent_role_id, child_role_id, created_on) VALUES ('2f804552-339c-4e72-8c9e-b8feef0099a3', '69f6b032-3989-4b06-be04-0e4bf5536997', '6b8ec23a-0bde-4d77-98df-0ed0b763ba69', '2015-04-13T09:00:00');

COMMIT;
