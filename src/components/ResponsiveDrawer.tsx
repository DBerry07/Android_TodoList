import {
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemButton,
  ListItemText,
  Box,
} from "@mui/material";
import { Inbox, Mail } from "@mui/icons-material";
import { FC } from "react";
import { drawerWidth } from "../constants";

const ResponsiveDrawer: FC = () => {
  const drawerContents = (
    <div>
      <List>
        {["Inbox", "Starred", "Send email", "Drafts"].map((text, index) => (
          <ListItem key={text} disablePadding>
            <ListItemButton>
              <ListItemIcon>
                {index % 2 === 0 ? <Inbox /> : <Mail />}
              </ListItemIcon>
              <ListItemText primary={text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </div>
  );

  return (
    <Box component="nav" sx={{ width: drawerWidth }}>
      <Drawer variant="permanent" open>
        {drawerContents}
      </Drawer>
    </Box>
  );
};

export default ResponsiveDrawer;
