UPDATE workspace_correction SET name = REPLACE(name, '"', '');
UPDATE workspace_correction SET name = REPLACE(name, '»', '');