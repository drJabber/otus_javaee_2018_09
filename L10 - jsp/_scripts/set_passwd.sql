update staff set passwd_hash=encode(digest('blah','sha1'),'hex') where id<0