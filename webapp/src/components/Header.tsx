import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import Typography from "@material-ui/core/Typography";
import {makeStyles, Theme} from "@material-ui/core/styles";
import {useDrawerContext} from "../contexts/drawer-context";
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import AccountCircle from '@material-ui/icons/AccountCircle';
import {Dispatch, useState} from "react";
import {api} from "../utils/http";
import {getToken} from "../useToken";

const useStyles = makeStyles((theme: Theme) => ({
    appBar: {
        background: theme.palette.primary.dark,
        color: theme.palette.secondary.light,
    },
    icon: {
        padding: theme.spacing(1),
    },
    title: {
        margin: "auto",
    },
}));

async function logout(token: string): Promise<void> {
    return api.post<DTO.RefreshTokenRequestTO, void>("/auth/signout", {
        token: token
    }).then(() => {
    });
}

const Header = ({setToken}: { setToken: Dispatch<DTO.SignInResponseTO | undefined> }) => {
    const classes = useStyles();
    const {isOpened, toggleIsOpened} = useDrawerContext();

    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

    const handleMenuClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    let open = Boolean(anchorEl);

    const handleMenuLogout = async () => {
        const token = getToken();
        if (token) {
            await logout(token.token);
            setToken(undefined);
        }
    }

    return (
        <AppBar className={classes.appBar}>
            <Toolbar>
                <IconButton
                    color="inherit"
                    onClick={() => toggleIsOpened(!isOpened)}
                    className={classes.icon}
                >
                    {isOpened ? <ChevronLeftIcon/> : <MenuIcon/>}
                </IconButton>
                <Typography variant="h6" className={classes.title}>
                    Header
                </Typography>

                <div>
                    <IconButton
                        aria-owns={open ? 'menu-appbar' : undefined}
                        aria-haspopup="true"
                        onClick={handleMenuClick}
                        color="inherit"
                    >
                        <AccountCircle/>
                    </IconButton>
                    <Menu
                        id="menu-appbar"
                        anchorEl={anchorEl}
                        anchorOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        open={open}
                        onClose={handleMenuClose}
                    >
                        <MenuItem onClick={handleMenuLogout}>Logout</MenuItem>
                    </Menu>
                </div>

            </Toolbar>
        </AppBar>
    );
};

export default Header;
